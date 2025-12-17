#!/usr/bin/env python3
"""
Generate iOS app icon for DukeStar app.
Creates a 1024x1024 PNG matching the Android neon style.
"""

from PIL import Image, ImageDraw, ImageFilter

def create_dukestar_icon(size=1024):
    """Create a DukeStar app icon matching the Android adaptive icon appearance."""

    # Colors matching Android screenshot appearance
    dark_purple = (26, 10, 46)      # #1A0A2E - speaker cabinet fill
    magenta = (255, 0, 255)         # #FF00FF - woofer
    cyan = (0, 255, 255)            # #00FFFF - tweeter
    orange = (255, 107, 53)         # #FF6B35 - cabinet outline
    lime_green = (0, 255, 0)        # #00FF00 - main glow (as seen in Android)
    # Create image with LIME GREEN background (fills corners too)
    img = Image.new('RGBA', (size, size), lime_green + (255,))
    draw = ImageDraw.Draw(img)

    # Center point
    cx, cy = size // 2, size // 2

    # Scale factor based on Android 108dp viewport
    s = size / 108

    # === NEON RAYS (matching Android) ===
    line_width = int(3 * s)
    # Vertical rays (magenta) - from center to edges
    draw.line([cx, cy, cx, int(3 * s)], fill=magenta, width=line_width)
    draw.line([cx, cy, cx, size - int(3 * s)], fill=magenta, width=line_width)
    # Horizontal rays (cyan)
    draw.line([cx, cy, int(3 * s), cy], fill=cyan, width=line_width)
    draw.line([cx, cy, size - int(3 * s), cy], fill=cyan, width=line_width)

    # === SPEAKERS (much larger to match Android) ===
    scale = 1.5  # Even larger speakers to fill more of the icon

    def draw_speaker(center_x, center_y, rotation):
        """Draw a neon outline speaker cabinet."""
        speaker_size = int(80 * s)
        speaker_img = Image.new('RGBA', (speaker_size, speaker_size), (0, 0, 0, 0))
        speaker_draw = ImageDraw.Draw(speaker_img)

        scx, scy = speaker_size // 2, speaker_size // 2

        # Speaker cabinet dimensions (matching Android: 28x52 in 108dp space)
        cab_w = int(28 * s * scale)
        cab_h = int(52 * s * scale)

        # Speaker cabinet fill (dark purple)
        speaker_draw.rectangle(
            [scx - cab_w//2, scy - cab_h//2, scx + cab_w//2, scy + cab_h//2],
            fill=dark_purple + (255,)
        )

        # Cabinet outline (orange)
        speaker_draw.rectangle(
            [scx - cab_w//2, scy - cab_h//2, scx + cab_w//2, scy + cab_h//2],
            outline=orange + (255,),
            width=max(int(2.5 * s * scale), 2)
        )

        # 3D edge effects (semi-transparent orange) - right and top edges
        edge_color = orange + (136,)  # 88 hex = 136 dec
        edge_width = max(int(1.5 * s * scale), 1)
        # Right edge going up-right
        x1 = scx + cab_w//2
        y1 = scy - cab_h//2
        x2 = x1 + int(6 * s * scale)
        y2 = y1 - int(6 * s * scale)
        speaker_draw.line([x1, y1, x2, y2], fill=edge_color, width=edge_width)
        speaker_draw.line([x2, y2, x2, scy + cab_h//2 - int(6 * s * scale)], fill=edge_color, width=edge_width)
        speaker_draw.line([x1, scy + cab_h//2, x2, scy + cab_h//2 - int(6 * s * scale)], fill=edge_color, width=edge_width)
        # Top edge
        speaker_draw.line([scx - cab_w//2, y1, scx - cab_w//2 + int(6 * s * scale), y2], fill=edge_color, width=edge_width)
        speaker_draw.line([scx - cab_w//2 + int(6 * s * scale), y2, x2, y2], fill=edge_color, width=edge_width)

        # Tweeter (top speaker) - cyan neon circles
        tweeter_y = scy - int(12 * s * scale)
        # Outer circle
        r1 = int(10 * s * scale)
        speaker_draw.ellipse(
            [scx - r1, tweeter_y - r1, scx + r1, tweeter_y + r1],
            outline=cyan + (255,), width=max(int(2 * s * scale), 2)
        )
        # Middle circle (semi-transparent)
        r2 = int(6 * s * scale)
        speaker_draw.ellipse(
            [scx - r2, tweeter_y - r2, scx + r2, tweeter_y + r2],
            outline=cyan + (136,), width=max(int(1.5 * s * scale), 1)
        )
        # Center dot (filled)
        r3 = int(2 * s * scale)
        speaker_draw.ellipse(
            [scx - r3, tweeter_y - r3, scx + r3, tweeter_y + r3],
            fill=cyan + (255,)
        )

        # Woofer (bottom speaker) - magenta with green accent rings (matching Android)
        woofer_y = scy + int(10 * s * scale)
        # Outer circle (magenta)
        r1 = int(12 * s * scale)
        speaker_draw.ellipse(
            [scx - r1, woofer_y - r1, scx + r1, woofer_y + r1],
            outline=magenta + (255,), width=max(int(2.5 * s * scale), 2)
        )
        # Middle circle (lime green accent - as seen in Android)
        r2 = int(8 * s * scale)
        speaker_draw.ellipse(
            [scx - r2, woofer_y - r2, scx + r2, woofer_y + r2],
            outline=lime_green + (200,), width=max(int(1.5 * s * scale), 1)
        )
        # Inner circle (magenta)
        r3 = int(4 * s * scale)
        speaker_draw.ellipse(
            [scx - r3, woofer_y - r3, scx + r3, woofer_y + r3],
            outline=magenta + (200,), width=max(int(1 * s * scale), 1)
        )
        # Center dot (filled lime green)
        r4 = int(2 * s * scale)
        speaker_draw.ellipse(
            [scx - r4, woofer_y - r4, scx + r4, woofer_y + r4],
            fill=lime_green + (255,)
        )

        # Rotate speaker
        rotated = speaker_img.rotate(-rotation, resample=Image.BICUBIC, expand=False)

        # Paste onto main image
        paste_x = int(center_x - speaker_size // 2)
        paste_y = int(center_y - speaker_size // 2)
        img_copy = img.copy()
        img_copy.paste(rotated, (paste_x, paste_y), rotated)
        return img_copy

    # Speaker positions (matching Android layout)
    left_x = cx - int(22 * s * scale)
    right_x = cx + int(22 * s * scale)

    # Draw left speaker (tilted -12 degrees)
    img = draw_speaker(left_x, cy, -12)

    # Draw right speaker (tilted +12 degrees)
    img = draw_speaker(right_x, cy, 12)

    # Convert to RGB for final output
    return img.convert('RGB')


def main():
    import os

    output_dir = "/Users/user289697/Documents/boombox/boombox/iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"
    os.makedirs(output_dir, exist_ok=True)
    output_path = os.path.join(output_dir, "icon_1024.png")

    print("Creating DukeStar iOS app icon...")
    icon = create_dukestar_icon(1024)

    # Save as PNG with sRGB color profile
    icon.save(output_path, "PNG", optimize=False)
    print(f"Icon saved to: {output_path}")

    saved = Image.open(output_path)
    print(f"Verified: {saved.size[0]}x{saved.size[1]} {saved.mode}")


if __name__ == "__main__":
    main()
