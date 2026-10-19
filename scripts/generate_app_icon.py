"""Generate the Flow Music launcher/app icon set.

Design: a bold five-bar soundwave ("flow") with rounded caps, filled by the app's
violet -> cyan gradient, on a deep near-black tile with a soft violet glow. Bar
geometry is defined on a 108-unit canvas so the mark fits the Android adaptive
icon safe zone (central 66/108) and is never clipped by the launcher mask.

Run from the repo root:  python3 scripts/generate_app_icon.py
"""

from functools import lru_cache

from PIL import Image, ImageDraw, ImageFilter, ImageFont

# Brand palette (matches docs/styles.css and the widget soundwave logo)
VIOLET = (124, 92, 255)
CYAN = (34, 211, 238)
DEEP = (11, 13, 22)

SS = 4  # supersample factor for antialiasing
BARS = [26, 44, 62, 40, 24]  # heights on a 108-unit canvas, tallest in the middle


def lerp(a, b, t):
    return tuple(round(a[i] + (b[i] - a[i]) * t) for i in range(3))


@lru_cache(maxsize=8)
def gradient(w, h, c0, c1):
    """Diagonal linear gradient, c0 at bottom-left, c1 at top-right."""
    img = Image.new("RGB", (w, h))
    px = img.load()
    for y in range(h):
        ty = 1 - y / max(h - 1, 1)
        for x in range(w):
            px[x, y] = lerp(c0, c1, (x / max(w - 1, 1) + ty) / 2)
    return img


@lru_cache(maxsize=4)
def bar_boxes(s):
    """Bar rectangles on an s x s canvas, plus the cap radius."""
    u = s / 108.0
    bw, gap = 9 * u, 5 * u
    total = len(BARS) * bw + (len(BARS) - 1) * gap
    x0, cy = (s - total) / 2, s / 2
    boxes = []
    for i, hgt in enumerate(BARS):
        h = hgt * u
        x = x0 + i * (bw + gap)
        boxes.append((x, cy - h / 2, x + bw, cy + h / 2))
    return boxes, bw / 2


def mark_at(s, glow=None):
    """Soundwave mark rendered on an s x s transparent canvas."""
    boxes, radius = bar_boxes(s)

    mask = Image.new("L", (s, s), 0)
    md = ImageDraw.Draw(mask)
    for box in boxes:
        md.rounded_rectangle(box, radius=radius, fill=255)

    out = Image.new("RGBA", (s, s), (0, 0, 0, 0))
    out.paste(gradient(s, s, VIOLET, CYAN), (0, 0), mask)

    if glow:
        halo = Image.new("RGBA", (s, s), (0, 0, 0, 0))
        blurred = mask.filter(ImageFilter.GaussianBlur(radius=s * 0.035))
        halo.paste(Image.new("RGBA", (s, s), glow + (255,)), (0, 0), blurred)
        halo.putalpha(halo.getchannel("A").point(lambda v: int(v * 0.55)))
        out = Image.alpha_composite(halo, out)

    return out


def mark(size, glow=VIOLET):
    return mark_at(size * SS, glow).resize((size, size), Image.LANCZOS)


def dark_tile_at(s, radius_ratio=0.235):
    """Deep tile with a violet/cyan wash. radius_ratio None => circle."""
    shape = Image.new("L", (s, s), 0)
    sd = ImageDraw.Draw(shape)
    if radius_ratio is None:
        sd.ellipse([0, 0, s - 1, s - 1], fill=255)
    else:
        sd.rounded_rectangle([0, 0, s - 1, s - 1], radius=int(s * radius_ratio), fill=255)

    base = Image.new("RGBA", (s, s), DEEP + (255,))
    wash = Image.new("RGBA", (s, s), (0, 0, 0, 0))
    wd = ImageDraw.Draw(wash)
    wd.ellipse([-s * 0.5, -s * 0.6, s * 0.85, s * 0.7], fill=VIOLET + (90,))
    wd.ellipse([s * 0.15, s * 0.35, s * 1.5, s * 1.5], fill=CYAN + (60,))
    wash = wash.filter(ImageFilter.GaussianBlur(radius=s * 0.10))
    base = Image.alpha_composite(base, wash)

    out = Image.new("RGBA", (s, s), (0, 0, 0, 0))
    out.paste(base, (0, 0), shape)
    return out


def tile(size, radius_ratio=0.235):
    s = size * SS
    out = dark_tile_at(s, radius_ratio)
    out.alpha_composite(mark_at(s, glow=VIOLET))
    return out.resize((size, size), Image.LANCZOS)


def monochrome_at(s):
    boxes, radius = bar_boxes(s)
    mask = Image.new("L", (s, s), 0)
    md = ImageDraw.Draw(mask)
    for box in boxes:
        md.rounded_rectangle(box, radius=radius, fill=255)
    out = Image.new("RGBA", (s, s), (0, 0, 0, 0))
    out.paste(Image.new("RGBA", (s, s), (255, 255, 255, 255)), (0, 0), mask)
    return out


def banner_at(s):
    """Android TV banner: mark plus wordmark, composed at 16:9 rather than
    cropped from the square tile, so the lockup reads horizontally."""
    w, h = s * 16 // 9, s
    out = Image.new("RGBA", (w, h), DEEP + (255,))

    wash = Image.new("RGBA", (w, h), (0, 0, 0, 0))
    wd = ImageDraw.Draw(wash)
    wd.ellipse([-w * 0.25, -h * 0.7, w * 0.45, h * 0.9], fill=VIOLET + (85,))
    wd.ellipse([w * 0.55, h * 0.1, w * 1.2, h * 1.4], fill=CYAN + (55,))
    out = Image.alpha_composite(out, wash.filter(ImageFilter.GaussianBlur(radius=h * 0.12)))

    m = int(h * 0.46)
    out.alpha_composite(mark_at(m, glow=VIOLET), (int(h * 0.20), (h - m) // 2))

    font = ImageFont.truetype("app/src/main/res/font/plus_jakarta_sans.ttf", int(h * 0.235))
    d = ImageDraw.Draw(out)
    tx = int(h * 0.20) + m + int(h * 0.13)
    box = d.textbbox((tx, 0), "Flow Music", font=font)
    ty = (h - (box[3] - box[1])) // 2 - box[1]

    text_mask = Image.new("L", (w, h), 0)
    ImageDraw.Draw(text_mask).text((tx, ty), "Flow Music", font=font, fill=255)
    out.paste(gradient(w, h, VIOLET, CYAN), (0, 0), text_mask)
    return out


def write(img, path):
    img.save(path, "PNG", optimize=True)


def main():
    root = "app/src/main/res"
    legacy = {"ldpi": 36, "mdpi": 48, "hdpi": 72, "xhdpi": 96, "xxhdpi": 144, "xxxhdpi": 192}
    adaptive = {"mdpi": 108, "hdpi": 162, "xhdpi": 216, "xxhdpi": 324, "xxxhdpi": 432}

    for d, px in legacy.items():
        write(tile(px), f"{root}/mipmap-{d}/ic_launcher.png")
        write(tile(px, radius_ratio=None), f"{root}/mipmap-{d}/ic_launcher_round.png")

    for d, px in adaptive.items():
        s = px * SS
        # No glow here: the adaptive safe zone is the central 66/108, and a halo that
        # bleeds past it gets sliced by the launcher mask into a hard visible edge.
        fg = mark_at(s).resize((px, px), Image.LANCZOS)
        mono = monochrome_at(s).resize((px, px), Image.LANCZOS)
        write(fg, f"{root}/mipmap-{d}/ic_launcher_foreground.png")
        write(mono, f"{root}/mipmap-{d}/ic_launcher_monochrome.png")

    # Drawable copies used in-app, by widgets and by notifications
    write(tile(512, radius_ratio=0.22), f"{root}/drawable/icon.png")
    write(mark(432), f"{root}/drawable/ic_launcher_foreground.png")
    write(mark_at(1080, glow=VIOLET), f"{root}/drawable/ic_launcher_nobg.png")
    write(monochrome_at(432 * SS).resize((432, 432), Image.LANCZOS), f"{root}/drawable/ic_launcher_monochrome.png")
    write(mark_at(96), f"{root}/drawable/ic_qs_flow_logo.png")

    # Android TV banner (@mipmap/tv_banner, referenced from the manifest)
    write(banner_at(180), f"{root}/mipmap-xhdpi/tv_banner.png")

    # Landing page favicon / header logo (docs/assets/Flow-new.png)
    write(tile(512, radius_ratio=0.22), "docs/assets/Flow-new.png")

    print("icons generated")


if __name__ == "__main__":
    main()
