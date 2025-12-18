#!/usr/bin/env python3
"""
Generate iOS app icon for DukeStar - Gradient background with dark squircle frame
Magenta-to-cyan gradient with dark squircle creating a gradient ring/frame effect.
Output: 1024x1024 PNG
"""

from PIL import Image, ImageDraw
import math

# Icon size
SIZE = 1024

# Colors
BACKGROUND = (13, 2, 33)  # #0D0221 - Dark purple
MAGENTA = (255, 0, 255)  # #FF00FF
CYAN = (0, 255, 255)  # #00FFFF
WHITE = (255, 255, 255)  # #FFFFFF
ORANGE = (255, 107, 53)  # #FF6B35


def draw_rounded_rect(draw, bbox, radius, fill=None):
    """Draw a rounded rectangle."""
    x1, y1, x2, y2 = bbox
    r = min(radius, (x2-x1)//2, (y2-y1)//2)
    if r < 1:
        r = 1

    if fill:
        draw.rectangle([x1 + r, y1, x2 - r, y2], fill=fill)
        draw.rectangle([x1, y1 + r, x2, y2 - r], fill=fill)
        draw.pieslice([x1, y1, x1 + r * 2, y1 + r * 2], 180, 270, fill=fill)
        draw.pieslice([x2 - r * 2, y1, x2, y1 + r * 2], 270, 360, fill=fill)
        draw.pieslice([x1, y2 - r * 2, x1 + r * 2, y2], 90, 180, fill=fill)
        draw.pieslice([x2 - r * 2, y2 - r * 2, x2, y2], 0, 90, fill=fill)


def lerp_color(c1, c2, t):
    """Linear interpolation between two colors."""
    return tuple(int(c1[i] + (c2[i] - c1[i]) * t) for i in range(3))


def draw_squircle(draw, center_x, center_y, size, fill, n=5):
    """
    Draw a squircle (superellipse) shape.
    n controls the squareness: 2=ellipse, higher=more square corners
    Apple uses approximately n=5 for iOS icons.
    """
    points = []
    half_size = size // 2

    for angle in range(360):
        rad = math.radians(angle)
        cos_a = math.cos(rad)
        sin_a = math.sin(rad)

        # Superellipse formula: |x/a|^n + |y/b|^n = 1
        # Solved for radius: r = (|cos|^n + |sin|^n)^(-1/n)
        r = (abs(cos_a)**n + abs(sin_a)**n)**(-1/n)

        x = center_x + int(half_size * r * cos_a)
        y = center_y + int(half_size * r * sin_a)
        points.append((x, y))

    draw.polygon(points, fill=fill)


def main():
    """
    Design: Gradient background with dark squircle frame.
    The gradient shows as a ring/border around the dark squircle where boombox sits.
    """
    img = Image.new('RGB', (SIZE, SIZE), MAGENTA)
    draw = ImageDraw.Draw(img)

    # === GRADIENT BACKGROUND (magenta to cyan, left to right) ===
    for x in range(SIZE):
        t = x / SIZE
        color = lerp_color(MAGENTA, CYAN, t)
        draw.line([(x, 0), (x, SIZE)], fill=color)

    # === DARK SQUIRCLE centered - creates gradient ring effect ===
    # Make dark squircle smaller to leave a visible gradient border (5% smaller)
    squircle_size = int(SIZE * 0.71)  # 5% smaller for thicker gradient frame
    draw_squircle(draw, SIZE // 2, SIZE // 2, squircle_size, BACKGROUND, n=5)

    # === BOOMBOX (5% smaller) ===
    boombox_scale = 0.71
    canvas_width = int(SIZE * boombox_scale)
    canvas_height = int(canvas_width / 1.6)
    center_x = SIZE // 2
    center_y = SIZE // 2

    # Body: 90% of canvas width, 50% of canvas height
    body_width = int(canvas_width * 0.9)
    body_height = int(canvas_height * 0.5)
    body_left = center_x - body_width // 2
    body_radius = int(canvas_height * 0.12)

    # Handle: 37.5% × 20% of canvas
    handle_width = int(canvas_width * 0.375)
    handle_height = int(canvas_height * 0.2)
    handle_left = center_x - handle_width // 2
    handle_radius = int(canvas_height * 0.1)

    # Vertical positioning
    handle_extension = int(canvas_height * 0.16)
    total_height = body_height + handle_extension

    boombox_top = center_y - total_height // 2
    handle_top = boombox_top
    body_top = handle_top + handle_extension

    # Draw body first
    draw_rounded_rect(draw, (body_left, body_top,
                            body_left + body_width, body_top + body_height),
                      body_radius, fill=WHITE)

    # Draw handle
    draw_rounded_rect(draw, (handle_left, handle_top,
                            handle_left + handle_width, handle_top + handle_height),
                      handle_radius, fill=WHITE)

    # Speakers
    speaker_radius = int(body_height * 0.35)
    speaker_inner_radius = int(speaker_radius * 0.4)
    speaker_y = body_top + body_height // 2

    # Left speaker
    left_speaker_x = body_left + int(body_width * 0.25)
    draw.ellipse([left_speaker_x - speaker_radius, speaker_y - speaker_radius,
                  left_speaker_x + speaker_radius, speaker_y + speaker_radius], fill=MAGENTA)
    draw.ellipse([left_speaker_x - speaker_inner_radius, speaker_y - speaker_inner_radius,
                  left_speaker_x + speaker_inner_radius, speaker_y + speaker_inner_radius], fill=WHITE)

    # Right speaker
    right_speaker_x = body_left + int(body_width * 0.75)
    draw.ellipse([right_speaker_x - speaker_radius, speaker_y - speaker_radius,
                  right_speaker_x + speaker_radius, speaker_y + speaker_radius], fill=CYAN)
    draw.ellipse([right_speaker_x - speaker_inner_radius, speaker_y - speaker_inner_radius,
                  right_speaker_x + speaker_inner_radius, speaker_y + speaker_inner_radius], fill=WHITE)

    # Cassette
    cassette_width = int(canvas_width * 0.1875)
    cassette_height = int(canvas_height * 0.24)
    cassette_left = center_x - cassette_width // 2
    cassette_top = speaker_y - cassette_height // 2
    cassette_radius = int(canvas_height * 0.04)

    draw_rounded_rect(draw, (cassette_left, cassette_top,
                            cassette_left + cassette_width, cassette_top + cassette_height),
                      cassette_radius, fill=ORANGE)

    # Save the icon
    output_path = '/home/mandroid/Videos/hitit-logo/iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/icon_1024.png'
    img.save(output_path, 'PNG')
    print(f"iOS icon saved to: {output_path}")

    # Also save a copy to tmp for preview
    preview_path = '/home/mandroid/Videos/hitit-logo/tmp/ios_icon_preview.png'
    img.save(preview_path, 'PNG')
    print(f"Preview saved to: {preview_path}")


if __name__ == '__main__':
    main()
