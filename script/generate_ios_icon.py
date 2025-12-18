#!/usr/bin/env python3
"""
Generate iOS app icon for DukeStar - Pixel-perfect match to Canvas
Uses exact proportions from SplashScreen BoomboxIcon Canvas drawing
Output: 1024x1024 PNG
"""

from PIL import Image, ImageDraw

# Icon size
SIZE = 1024

# Colors
BACKGROUND = (13, 2, 33)  # #0D0221
FRAME_FILL = (42, 24, 72)  # #2A1848
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


def draw_gradient_frame_stroke(img, bbox, radius, stroke_width):
    """Draw gradient stroke around rounded rectangle."""
    x1, y1, x2, y2 = bbox
    width = x2 - x1
    draw = ImageDraw.Draw(img)
    r = radius

    # Top and bottom edges with gradient
    for x in range(int(x1 + r), int(x2 - r)):
        t = (x - x1) / width
        color = lerp_color(MAGENTA, CYAN, t)
        for sw in range(stroke_width):
            draw.point((x, y1 + sw), fill=color)
            draw.point((x, y2 - sw - 1), fill=color)

    # Left edge - magenta
    draw.rectangle([x1, y1 + r, x1 + stroke_width, y2 - r], fill=MAGENTA)

    # Right edge - cyan
    draw.rectangle([x2 - stroke_width, y1 + r, x2, y2 - r], fill=CYAN)

    # Corners
    draw.arc([x1, y1, x1 + r * 2, y1 + r * 2], 180, 270, fill=MAGENTA, width=stroke_width)
    draw.arc([x2 - r * 2, y1, x2, y1 + r * 2], 270, 360, fill=CYAN, width=stroke_width)
    draw.arc([x1, y2 - r * 2, x1 + r * 2, y2], 90, 180, fill=MAGENTA, width=stroke_width)
    draw.arc([x2 - r * 2, y2 - r * 2, x2, y2], 0, 90, fill=CYAN, width=stroke_width)


def main():
    """
    Pixel-perfect recreation of SplashScreen Canvas boombox.

    Canvas proportions (160×100dp):
    - Body: 90% width × 50% height = 144×50dp, corner radius 12dp
    - Handle: 60×20dp, centered, 16dp above body, corner radius 10dp
    - Speakers: radius = 35% of body height = 17.5dp
    - Speaker inner: 40% of outer radius = 7dp
    - Speaker positions: 25% and 75% of body width from body left
    - Cassette: 30×24dp centered, corner radius 4dp
    """
    img = Image.new('RGB', (SIZE, SIZE), BACKGROUND)
    draw = ImageDraw.Draw(img)

    # Frame proportions (200×200dp in splash, maps to center of 1024)
    # Frame takes ~60% of icon size, centered
    frame_size = int(SIZE * 0.6)
    frame_margin = (SIZE - frame_size) // 2
    frame_x1 = frame_margin
    frame_y1 = frame_margin
    frame_x2 = SIZE - frame_margin
    frame_y2 = SIZE - frame_margin
    frame_radius = int(frame_size * 0.16)  # 32/200 = 16%
    stroke_width = int(frame_size * 0.015)  # 3/200 = 1.5%

    # Draw frame fill
    draw_rounded_rect(draw, (frame_x1, frame_y1, frame_x2, frame_y2),
                      frame_radius, fill=FRAME_FILL)

    # Draw gradient stroke
    draw_gradient_frame_stroke(img, (frame_x1, frame_y1, frame_x2, frame_y2),
                               frame_radius, stroke_width)

    # === BOOMBOX (matching Canvas exactly) ===
    # Canvas is 160×100 inside 200×200 frame = 80% × 50%
    # Canvas center is at frame center

    canvas_width = int(frame_size * 0.8)   # 160/200 = 80%
    canvas_height = int(frame_size * 0.5)  # 100/200 = 50%
    canvas_center_x = SIZE // 2
    canvas_center_y = SIZE // 2

    # Body: 90% of canvas width, 50% of canvas height
    body_width = int(canvas_width * 0.9)
    body_height = int(canvas_height * 0.5)
    body_left = canvas_center_x - body_width // 2
    body_top = canvas_center_y - body_height // 2
    body_radius = int(canvas_height * 0.12)  # 12/100 = 12%

    # Handle: 60×20dp = 37.5% × 20% of canvas
    # Handle top is at boxTop - 16dp, height is 20dp
    # So handle bottom = boxTop - 16 + 20 = boxTop + 4 (overlaps body by 4dp)
    handle_width = int(canvas_width * 0.375)
    handle_height = int(canvas_height * 0.2)
    handle_left = canvas_center_x - handle_width // 2
    handle_top = body_top - int(canvas_height * 0.16)  # 16dp above body top
    handle_radius = int(canvas_height * 0.1)  # 10/100 = 10%

    # Draw body first (handle will overlap it)
    draw_rounded_rect(draw, (body_left, body_top,
                            body_left + body_width, body_top + body_height),
                      body_radius, fill=WHITE)

    # Draw handle (overlaps body top)
    draw_rounded_rect(draw, (handle_left, handle_top,
                            handle_left + handle_width, handle_top + handle_height),
                      handle_radius, fill=WHITE)


    # Speakers: radius = 35% of body height
    speaker_radius = int(body_height * 0.35)
    speaker_inner_radius = int(speaker_radius * 0.4)
    speaker_y = canvas_center_y

    # Left speaker at 25% of body width
    left_speaker_x = body_left + int(body_width * 0.25)
    draw.ellipse([left_speaker_x - speaker_radius, speaker_y - speaker_radius,
                  left_speaker_x + speaker_radius, speaker_y + speaker_radius], fill=MAGENTA)
    draw.ellipse([left_speaker_x - speaker_inner_radius, speaker_y - speaker_inner_radius,
                  left_speaker_x + speaker_inner_radius, speaker_y + speaker_inner_radius], fill=WHITE)

    # Right speaker at 75% of body width
    right_speaker_x = body_left + int(body_width * 0.75)
    draw.ellipse([right_speaker_x - speaker_radius, speaker_y - speaker_radius,
                  right_speaker_x + speaker_radius, speaker_y + speaker_radius], fill=CYAN)
    draw.ellipse([right_speaker_x - speaker_inner_radius, speaker_y - speaker_inner_radius,
                  right_speaker_x + speaker_inner_radius, speaker_y + speaker_inner_radius], fill=WHITE)

    # Cassette: 30×24dp = 18.75% × 24% of canvas, centered
    cassette_width = int(canvas_width * 0.1875)
    cassette_height = int(canvas_height * 0.24)
    cassette_left = canvas_center_x - cassette_width // 2
    cassette_top = canvas_center_y - cassette_height // 2
    cassette_radius = int(canvas_height * 0.04)  # 4/100 = 4%

    draw_rounded_rect(draw, (cassette_left, cassette_top,
                            cassette_left + cassette_width, cassette_top + cassette_height),
                      cassette_radius, fill=ORANGE)

    # Save the icon
    output_path = '/home/mandroid/Videos/hitit/iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/icon_1024.png'
    img.save(output_path, 'PNG')
    print(f"iOS icon saved to: {output_path}")

    # Also save a copy to tmp for preview
    preview_path = '/home/mandroid/Videos/hitit/tmp/ios_icon_preview.png'
    img.save(preview_path, 'PNG')
    print(f"Preview saved to: {preview_path}")


if __name__ == '__main__':
    main()
