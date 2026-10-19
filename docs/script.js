/* ============================================================
   Flow Music — landing page behaviour
   ============================================================ */
const REPO = "NETO-DEV66/Flow-music-";
const RELEASES = `https://github.com/${REPO}/releases`;
const LATEST_API = `https://api.github.com/repos/${REPO}/releases/latest`;

const $ = (sel) => document.querySelector(sel);
const $$ = (sel) => Array.from(document.querySelectorAll(sel));

/* ---------- sticky nav ---------- */
const nav = $("#nav");
const onScroll = () => nav.classList.toggle("scrolled", window.scrollY > 18);
onScroll();
window.addEventListener("scroll", onScroll, { passive: true });

/* ---------- mobile menu ---------- */
const burger = $("#burger");
const navLinks = $("#navLinks");
burger.addEventListener("click", () => {
  const open = navLinks.classList.toggle("open");
  burger.setAttribute("aria-expanded", String(open));
});
navLinks.addEventListener("click", (e) => {
  if (e.target.tagName === "A") {
    navLinks.classList.remove("open");
    burger.setAttribute("aria-expanded", "false");
  }
});

/* ---------- reveal on scroll ---------- */
const io = new IntersectionObserver(
  (entries) => {
    entries.forEach((entry) => {
      if (!entry.isIntersecting) return;
      const el = entry.target;
      const siblings = Array.from(el.parentElement?.children ?? []).indexOf(el);
      el.style.transitionDelay = `${Math.min(siblings, 6) * 70}ms`;
      el.classList.add("in");
      io.unobserve(el);
    });
  },
  { threshold: 0.12, rootMargin: "0px 0px -8% 0px" }
);
$$(".reveal").forEach((el) => io.observe(el));

/* ---------- pointer glow on cards ---------- */
$$(".card").forEach((card) => {
  card.addEventListener("pointermove", (e) => {
    const r = card.getBoundingClientRect();
    card.style.setProperty("--mx", `${e.clientX - r.left}px`);
    card.style.setProperty("--my", `${e.clientY - r.top}px`);
  });
});

/* ---------- animated counters ---------- */
const countIO = new IntersectionObserver(
  (entries) => {
    entries.forEach((entry) => {
      if (!entry.isIntersecting) return;
      const el = entry.target;
      const target = Number(el.dataset.count);
      const suffix = target === 100 ? "" : "";
      const duration = 1300;
      const start = performance.now();
      const tick = (now) => {
        const p = Math.min((now - start) / duration, 1);
        const eased = 1 - Math.pow(1 - p, 3);
        el.textContent = Math.round(target * eased) + suffix;
        if (p < 1) requestAnimationFrame(tick);
      };
      requestAnimationFrame(tick);
      countIO.unobserve(el);
    });
  },
  { threshold: 0.5 }
);
$$("[data-count]").forEach((el) => countIO.observe(el));

/* ---------- back to top ---------- */
const toTop = $("#toTop");
toTop.addEventListener("click", () => window.scrollTo({ top: 0, behavior: "smooth" }));
window.addEventListener(
  "scroll",
  () => toTop.classList.toggle("show", window.scrollY > 700),
  { passive: true }
);

/* ---------- copy checksum ---------- */
const copyBtn = $("#copySha");
copyBtn?.addEventListener("click", async () => {
  const value = $("#dlSha").textContent.trim();
  if (!value || value === "loading…") return;
  try {
    await navigator.clipboard.writeText(value);
    copyBtn.textContent = "copied";
  } catch {
    copyBtn.textContent = "select it";
  }
  setTimeout(() => (copyBtn.textContent = "copy"), 1600);
});

/* ---------- live release data ---------- */
const human = (bytes) => {
  if (!bytes) return "—";
  const units = ["B", "KB", "MB", "GB"];
  let i = 0;
  let n = bytes;
  while (n >= 1024 && i < units.length - 1) {
    n /= 1024;
    i += 1;
  }
  return `${n.toFixed(i ? 1 : 0)} ${units[i]}`;
};

async function loadRelease() {
  try {
    const res = await fetch(LATEST_API, { headers: { Accept: "application/vnd.github+json" } });
    if (!res.ok) throw new Error(String(res.status));
    const release = await res.json();

    const asset =
      (release.assets || []).find((a) => /\.apk$/i.test(a.name)) || (release.assets || [])[0];
    const version = release.tag_name || release.name || "";
    const size = asset ? human(asset.size) : "—";
    const url = asset?.browser_download_url || release.html_url || RELEASES;

    const sha =
      (asset && (release.body || "").match(new RegExp(`${asset.name}[^\\n]*?([a-f0-9]{64})`, "i"))?.[1]) ||
      (release.body || "").match(/\b([a-f0-9]{64})\b/i)?.[1] ||
      "see release notes";

    // version + links
    if (version) {
      $("#versionPill").textContent = `${version} · free & open source`;
      $("#statVersion").textContent = version;
      $("#dlVersion").textContent = version;
      $("#dlLabel").textContent = `Download ${version}`;
    }
    $("#dlSize").textContent = size;
    $("#statSize").textContent = size;
    if (asset) $("#dlFile").textContent = asset.name;

    const shaEl = $("#dlSha");
    shaEl.textContent = sha;
    shaEl.title = sha;

    [$("#downloadBtn"), $("#downloadBtn2"), $("#downloadBtn3")].forEach((a) => {
      if (a) a.href = url;
    });
  } catch {
    // Offline or rate-limited: the static fallbacks in the HTML remain valid.
    $("#dlSha").textContent = "see release notes";
  }
}

loadRelease();
