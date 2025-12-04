#!/usr/bin/env python3
"""
Deezer Matcher for Hitster Cards
Takes extracted card data and finds matching Deezer tracks.
"""

import json
import csv
import time
import urllib.parse
import requests
from pathlib import Path


DEEZER_API_BASE = "https://api.deezer.com"


def search_deezer(title: str, artist: str, year: int = None) -> dict | None:
    """Search Deezer for a track and return best match."""
    
    # Strategy 1: Search with artist and title
    query = f'artist:"{artist}" track:"{title}"'
    results = deezer_search(query)
    
    if results:
        return pick_best_match(results, title, artist, year)
    
    # Strategy 2: Simpler search without quotes
    query = f"{artist} {title}"
    results = deezer_search(query)
    
    if results:
        return pick_best_match(results, title, artist, year)
    
    # Strategy 3: Just title (for covers or compilation issues)
    results = deezer_search(title)
    
    if results:
        return pick_best_match(results, title, artist, year)
    
    return None


def deezer_search(query: str, limit: int = 10) -> list:
    """Execute a Deezer search query."""
    
    url = f"{DEEZER_API_BASE}/search"
    params = {
        "q": query,
        "limit": limit,
    }
    
    try:
        response = requests.get(url, params=params, timeout=10)
        response.raise_for_status()
        data = response.json()
        return data.get("data", [])
    except requests.RequestException as e:
        print(f"  Deezer API Fehler: {e}")
        return []


def pick_best_match(results: list, title: str, artist: str, year: int = None) -> dict:
    """Pick the best matching track from search results."""
    
    title_lower = title.lower()
    artist_lower = artist.lower()
    
    scored_results = []
    
    for track in results:
        score = 0
        track_title = track.get("title", "").lower()
        track_artist = track.get("artist", {}).get("name", "").lower()
        
        # Title match
        if title_lower == track_title:
            score += 100
        elif title_lower in track_title or track_title in title_lower:
            score += 50
        
        # Artist match
        if artist_lower == track_artist:
            score += 100
        elif artist_lower in track_artist or track_artist in artist_lower:
            score += 50
        
        # Prefer non-live, non-remix versions
        if "live" not in track_title and "remix" not in track_title:
            score += 10
        
        scored_results.append((score, track))
    
    # Sort by score descending
    scored_results.sort(key=lambda x: x[0], reverse=True)
    
    if scored_results:
        return scored_results[0][1]
    
    return results[0] if results else None


def format_deezer_result(track: dict) -> dict:
    """Extract relevant fields from Deezer track."""
    
    return {
        "deezer_id": track.get("id"),
        "deezer_title": track.get("title"),
        "deezer_artist": track.get("artist", {}).get("name"),
        "deezer_album": track.get("album", {}).get("title"),
        "deezer_link": track.get("link"),
        "deezer_preview": track.get("preview"),  # 30s preview URL
        "duration_sec": track.get("duration"),
    }


def match_cards_with_deezer(cards: list[dict]) -> list[dict]:
    """Match all cards with Deezer tracks."""
    
    matched_cards = []
    
    for i, card in enumerate(cards, 1):
        print(f"[{i}/{len(cards)}] {card['artist']} - {card['title']} ({card['year']})")
        
        if card.get('title') == 'UNLESBAR' or card.get('artist') == 'UNLESBAR':
            print("  → Übersprungen (unlesbar)")
            card['deezer_match'] = None
            matched_cards.append(card)
            continue
        
        match = search_deezer(card['title'], card['artist'], card.get('year'))
        
        if match:
            deezer_info = format_deezer_result(match)
            card.update(deezer_info)
            print(f"  → Gefunden: {deezer_info['deezer_artist']} - {deezer_info['deezer_title']}")
            print(f"     {deezer_info['deezer_link']}")
        else:
            card['deezer_id'] = None
            print("  → Kein Match gefunden")
        
        matched_cards.append(card)
        
        # Rate limiting: Deezer allows 50 requests per 5 seconds
        time.sleep(0.15)
    
    return matched_cards


def save_matched_results(cards: list[dict], output_base: str):
    """Save matched results to JSON and CSV."""
    
    # JSON
    json_path = f"{output_base}_deezer.json"
    with open(json_path, 'w', encoding='utf-8') as f:
        json.dump(cards, f, ensure_ascii=False, indent=2)
    print(f"\nJSON gespeichert: {json_path}")
    
    # CSV
    csv_path = f"{output_base}_deezer.csv"
    if cards:
        # Get all possible fieldnames
        fieldnames = list(cards[0].keys())
        for card in cards:
            for key in card.keys():
                if key not in fieldnames:
                    fieldnames.append(key)
        
        with open(csv_path, 'w', encoding='utf-8', newline='') as f:
            writer = csv.DictWriter(f, fieldnames=fieldnames)
            writer.writeheader()
            writer.writerows(cards)
        print(f"CSV gespeichert: {csv_path}")
    
    # Summary
    matched = sum(1 for c in cards if c.get('deezer_id'))
    print(f"\nErgebnis: {matched}/{len(cards)} Karten gematcht ({matched/len(cards)*100:.1f}%)")


def main():
    import sys
    
    if len(sys.argv) < 2:
        print("Verwendung: python deezer_matcher.py <hitster_cards.json>")
        print("\nNimmt die JSON-Ausgabe von hitster_ocr.py und findet Deezer-Matches.")
        sys.exit(1)
    
    input_path = sys.argv[1]
    
    if not Path(input_path).exists():
        print(f"Fehler: Datei nicht gefunden: {input_path}")
        sys.exit(1)
    
    with open(input_path, 'r', encoding='utf-8') as f:
        cards = json.load(f)
    
    print(f"Geladene Karten: {len(cards)}")
    print("="*50)
    
    matched_cards = match_cards_with_deezer(cards)
    
    output_base = Path(input_path).stem
    save_matched_results(matched_cards, output_base)


if __name__ == "__main__":
    main()
