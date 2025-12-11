#!/usr/bin/env python3
"""
Generate Kotlin code for MockHitsterCardRepository from Deezer JSON files.
"""
import json
import os

def escape_kotlin_string(s):
    """Escape special characters for Kotlin strings."""
    if s is None:
        return ""
    return s.replace('\\', '\\\\').replace('"', '\\"')

def generate_kotlin_entry(card):
    """Generate a single Kotlin map entry for a card."""
    card_id = str(card['id']).zfill(5)
    title = escape_kotlin_string(card.get('title', ''))
    artist = escape_kotlin_string(card.get('artist', ''))
    year = card.get('year', 0)
    deezer_id = card.get('deezer_id', 0)
    deezer_title = escape_kotlin_string(card.get('deezer_title', ''))
    deezer_artist = escape_kotlin_string(card.get('deezer_artist', ''))
    deezer_album = escape_kotlin_string(card.get('deezer_album', ''))

    return f'''        "{card_id}" to HitsterCard(
            hitsterId = "{card_id}",
            title = "{title}",
            artist = "{artist}",
            year = {year},
            deezerId = {deezer_id},
            deezerTitle = "{deezer_title}",
            deezerArtist = "{deezer_artist}",
            deezerAlbum = "{deezer_album}"
        )'''

def main():
    # Define the JSON files to process
    json_files = [
        'tmp/1-72/hitster-cards_deezer.json',
        'tmp/73-144/hitster-cards_deezer.json',
        'tmp/145-216/hitster-cards_deezer.json',
        'tmp/217-308/hitster-cards_deezer.json'
    ]

    all_cards = []

    for json_file in json_files:
        if os.path.exists(json_file):
            with open(json_file, 'r', encoding='utf-8') as f:
                cards = json.load(f)
                all_cards.extend(cards)
                print(f"Loaded {len(cards)} cards from {json_file}")
        else:
            print(f"Warning: {json_file} not found")

    # Sort by ID
    all_cards.sort(key=lambda x: x['id'])

    print(f"\nTotal cards: {len(all_cards)}")

    # Generate Kotlin entries
    kotlin_entries = []
    for card in all_cards:
        kotlin_entries.append(generate_kotlin_entry(card))

    # Join with commas
    kotlin_code = ',\n'.join(kotlin_entries)

    # Write to output file
    output_file = 'tmp/kotlin_card_entries.kt'
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("    private val cardDatabase: Map<String, HitsterCard> = mapOf(\n")
        f.write(kotlin_code)
        f.write("\n    )\n")

    print(f"\nKotlin code written to {output_file}")

if __name__ == '__main__':
    main()
