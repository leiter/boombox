package com.hitit.app.repository

import com.hitit.app.model.HitsterCard
import kotlinx.coroutines.delay

/**
 * Mock implementation of HitsterCardRepository.
 * Contains 308 Hitster cards with Deezer data.
 */
class MockHitsterCardRepository : HitsterCardRepository {

    // Simulated network delay (ms)
    private val simulatedDelay = 300L

    // Mock database of Hitster cards with Deezer matches
    private val cardDatabase: Map<String, HitsterCard> = mapOf(
        "00001" to HitsterCard(
            hitsterId = "00001",
            title = "Wann wird's mal wieder richtig Sommer?",
            artist = "Rudi Carrell",
            year = 1975,
            deezerId = 2305104805,
            deezerTitle = "Wann wird's mal wieder richtig Sommer",
            deezerArtist = "Rudi Carrell",
            deezerAlbum = "Das Beste von Rudi Carrell"
        ),
        "00002" to HitsterCard(
            hitsterId = "00002",
            title = "You Can Call Me Al",
            artist = "Paul Simon",
            year = 1986,
            deezerId = 6599483,
            deezerTitle = "You Can Call Me Al",
            deezerArtist = "Paul Simon",
            deezerAlbum = "The Essential Paul Simon"
        ),
        "00003" to HitsterCard(
            hitsterId = "00003",
            title = "Start Me Up",
            artist = "The Rolling Stones",
            year = 1981,
            deezerId = 1522727912,
            deezerTitle = "Start Me Up",
            deezerArtist = "The Rolling Stones",
            deezerAlbum = "Tattoo You (Super Deluxe)"
        ),
        "00004" to HitsterCard(
            hitsterId = "00004",
            title = "The Twist",
            artist = "Chubby Checker",
            year = 1960,
            deezerId = 64638140,
            deezerTitle = "The Twist",
            deezerArtist = "Chubby Checker",
            deezerAlbum = "Chubby Checker Classics"
        ),
        "00005" to HitsterCard(
            hitsterId = "00005",
            title = "Blurred Lines",
            artist = "Robin Thicke, T.I. & Pharrell",
            year = 2013,
            deezerId = 65444691,
            deezerTitle = "Blurred Lines",
            deezerArtist = "Robin Thicke",
            deezerAlbum = "Blurred Lines"
        ),
        "00006" to HitsterCard(
            hitsterId = "00006",
            title = "Hangover",
            artist = "Taio Cruz",
            year = 2011,
            deezerId = 14893478,
            deezerTitle = "Hangover",
            deezerArtist = "Taio Cruz",
            deezerAlbum = "TY.O (International Version)"
        ),
        "00007" to HitsterCard(
            hitsterId = "00007",
            title = "One More Time",
            artist = "Daft Punk",
            year = 2000,
            deezerId = 3135553,
            deezerTitle = "One More Time",
            deezerArtist = "Daft Punk",
            deezerAlbum = "Discovery"
        ),
        "00008" to HitsterCard(
            hitsterId = "00008",
            title = "China In Your Hand",
            artist = "T'Pau",
            year = 1987,
            deezerId = 3152784,
            deezerTitle = "China In Your Hand",
            deezerArtist = "T'pau",
            deezerAlbum = "Heart And Soul - The Very Best Of T'Pau"
        ),
        "00009" to HitsterCard(
            hitsterId = "00009",
            title = "The Best",
            artist = "Tina Turner",
            year = 1989,
            deezerId = 3142379,
            deezerTitle = "The Best",
            deezerArtist = "Tina Turner",
            deezerAlbum = "Tina!"
        ),
        "00010" to HitsterCard(
            hitsterId = "00010",
            title = "The Hustle",
            artist = "Van McCoy",
            year = 1975,
            deezerId = 2739276411,
            deezerTitle = "The Hustle",
            deezerArtist = "Van McCoy",
            deezerAlbum = "The Hustle"
        ),
        "00011" to HitsterCard(
            hitsterId = "00011",
            title = "Piece Of My Heart",
            artist = "Erma Franklin",
            year = 1967,
            deezerId = 9809444,
            deezerTitle = "Piece of My Heart",
            deezerArtist = "Erma Franklin",
            deezerAlbum = "Erma Franklin: Piece Of Her Heart - The Epic And Shout Years"
        ),
        "00012" to HitsterCard(
            hitsterId = "00012",
            title = "Hungry Eyes",
            artist = "Eric Carmen",
            year = 1987,
            deezerId = 13128242,
            deezerTitle = "Hungry Eyes (From \"Dirty Dancing\" Soundtrack)",
            deezerArtist = "Eric Carmen",
            deezerAlbum = "Dirty Dancing (Original Motion Picture Soundtrack)"
        ),
        "00013" to HitsterCard(
            hitsterId = "00013",
            title = "Karma Chameleon",
            artist = "Culture Club",
            year = 1983,
            deezerId = 3152791,
            deezerTitle = "Karma Chameleon",
            deezerArtist = "Culture Club",
            deezerAlbum = "Spin Dazzle - The Best Of Boy George And Culture Club"
        ),
        "00014" to HitsterCard(
            hitsterId = "00014",
            title = "99 Luftballons",
            artist = "Nena",
            year = 1983,
            deezerId = 639505182,
            deezerTitle = "99 Luftballons",
            deezerArtist = "Nena",
            deezerAlbum = "99 Luftballons"
        ),
        "00015" to HitsterCard(
            hitsterId = "00015",
            title = "Beinhart",
            artist = "Torfrock",
            year = 1990,
            deezerId = 481901362,
            deezerTitle = "Beinhart",
            deezerArtist = "Torfrock",
            deezerAlbum = "Beinhart"
        ),
        "00016" to HitsterCard(
            hitsterId = "00016",
            title = "In Da Club",
            artist = "50 Cent",
            year = 2003,
            deezerId = 1141668,
            deezerTitle = "In Da Club",
            deezerArtist = "50 Cent",
            deezerAlbum = "Get Rich Or Die Tryin'"
        ),
        "00017" to HitsterCard(
            hitsterId = "00017",
            title = "Don't Go",
            artist = "Yazoo",
            year = 1982,
            deezerId = 12650195,
            deezerTitle = "Don't Go (2008 Remaster)",
            deezerArtist = "Yazoo",
            deezerAlbum = "Essential: Eighties"
        ),
        "00018" to HitsterCard(
            hitsterId = "00018",
            title = "I Wanna Dance With Somebody (Who Loves Me)",
            artist = "Whitney Houston",
            year = 1987,
            deezerId = 75981528,
            deezerTitle = "I Wanna Dance with Somebody (Who Loves Me)",
            deezerArtist = "Whitney Houston",
            deezerAlbum = "Whitney"
        ),
        "00019" to HitsterCard(
            hitsterId = "00019",
            title = "7 Years",
            artist = "Lukas Graham",
            year = 2015,
            deezerId = 101742167,
            deezerTitle = "7 Years",
            deezerArtist = "Lukas Graham",
            deezerAlbum = "Lukas Graham (Blue Album)"
        ),
        "00020" to HitsterCard(
            hitsterId = "00020",
            title = "Sugar Baby",
            artist = "Peter Kraus",
            year = 1958,
            deezerId = 16638774,
            deezerTitle = "Sugar Baby",
            deezerArtist = "Peter Kraus",
            deezerAlbum = "Sugar Sugar Baby"
        ),
        "00021" to HitsterCard(
            hitsterId = "00021",
            title = "Lean On",
            artist = "Major Lazer, DJ Snake & MØ",
            year = 2015,
            deezerId = 445800112,
            deezerTitle = "Lean On",
            deezerArtist = "Major Lazer",
            deezerAlbum = "Peace Is The Mission (Remixes)"
        ),
        "00022" to HitsterCard(
            hitsterId = "00022",
            title = "Einmal um die Welt",
            artist = "Cro",
            year = 2012,
            deezerId = 3309927701,
            deezerTitle = "Einmal um die Welt",
            deezerArtist = "CRO",
            deezerAlbum = "Raop"
        ),
        "00023" to HitsterCard(
            hitsterId = "00023",
            title = "Ich will keine Schokolade",
            artist = "Trude Herr",
            year = 1960,
            deezerId = 2322567,
            deezerTitle = "Ich will keine Schokolade",
            deezerArtist = "Trude Herr",
            deezerAlbum = "Ich Will Keine Schokolade"
        ),
        "00024" to HitsterCard(
            hitsterId = "00024",
            title = "80 Millionen",
            artist = "Max Giesinger",
            year = 2016,
            deezerId = 518107802,
            deezerTitle = "80 Millionen",
            deezerArtist = "Max Giesinger",
            deezerAlbum = "80 Millionen"
        ),
        "00025" to HitsterCard(
            hitsterId = "00025",
            title = "Friday I'm In Love",
            artist = "The Cure",
            year = 1992,
            deezerId = 1123014,
            deezerTitle = "Friday I'm In Love",
            deezerArtist = "The Cure",
            deezerAlbum = "Wish"
        ),
        "00026" to HitsterCard(
            hitsterId = "00026",
            title = "What Is Love",
            artist = "Haddaway",
            year = 1993,
            deezerId = 2688767492,
            deezerTitle = "What Is Love",
            deezerArtist = "Haddaway",
            deezerAlbum = "What Is Love"
        ),
        "00027" to HitsterCard(
            hitsterId = "00027",
            title = "Auf uns",
            artist = "Andreas Bourani",
            year = 2014,
            deezerId = 77045434,
            deezerTitle = "Auf uns",
            deezerArtist = "Andreas Bourani",
            deezerAlbum = "Auf uns"
        ),
        "00028" to HitsterCard(
            hitsterId = "00028",
            title = "Don't Stop Believin'",
            artist = "Journey",
            year = 1981,
            deezerId = 625643,
            deezerTitle = "Don't Stop Believin'",
            deezerArtist = "Journey",
            deezerAlbum = "The Essential Journey"
        ),
        "00029" to HitsterCard(
            hitsterId = "00029",
            title = "Orinoco Flow",
            artist = "Enya",
            year = 1988,
            deezerId = 65728152,
            deezerTitle = "Orinoco Flow",
            deezerArtist = "Enya",
            deezerAlbum = "Paint the Sky with Stars"
        ),
        "00030" to HitsterCard(
            hitsterId = "00030",
            title = "Über den Wolken",
            artist = "Reinhard Mey",
            year = 1974,
            deezerId = 3297738,
            deezerTitle = "Über den Wolken",
            deezerArtist = "Reinhard Mey",
            deezerAlbum = "Die Grossen Erfolge"
        ),
        "00031" to HitsterCard(
            hitsterId = "00031",
            title = "Rehab",
            artist = "Amy Winehouse",
            year = 2006,
            deezerId = 2176852,
            deezerTitle = "Rehab",
            deezerArtist = "Amy Winehouse",
            deezerAlbum = "Back To Black"
        ),
        "00032" to HitsterCard(
            hitsterId = "00032",
            title = "Liebeskummer lohnt sich nicht",
            artist = "Siw Malmkvist",
            year = 1964,
            deezerId = 28126601,
            deezerTitle = "Liebeskummer Lohnt Sich Nicht",
            deezerArtist = "Siw Malmkvist",
            deezerAlbum = "Liebeskummer lohnt sich nicht"
        ),
        "00033" to HitsterCard(
            hitsterId = "00033",
            title = "You Make Me Feel (Mighty Real)",
            artist = "Sylvester",
            year = 1978,
            deezerId = 891619,
            deezerTitle = "You Make Me Feel (Mighty Real)",
            deezerArtist = "Sylvester",
            deezerAlbum = "The Original Hits"
        ),
        "00034" to HitsterCard(
            hitsterId = "00034",
            title = "Sweet Home Alabama",
            artist = "Lynyrd Skynyrd",
            year = 1974,
            deezerId = 24949681,
            deezerTitle = "Sweet Home Alabama",
            deezerArtist = "Lynyrd Skynyrd",
            deezerAlbum = "Second Helping (Expanded Edition)"
        ),
        "00035" to HitsterCard(
            hitsterId = "00035",
            title = "He Ain't Heavy He's My Brother",
            artist = "The Hollies",
            year = 1969,
            deezerId = 3161462,
            deezerTitle = "He Ain't Heavy He's My Brother (2003 Remaster)",
            deezerArtist = "The Hollies",
            deezerAlbum = "The Long Road Home 1963-2003 - 40th Anniversary Collection"
        ),
        "00036" to HitsterCard(
            hitsterId = "00036",
            title = "Eine neue Liebe ist wie ein neues Leben",
            artist = "Jürgen Marcus",
            year = 1972,
            deezerId = 539083782,
            deezerTitle = "Eine neue Liebe ist wie ein neues Leben",
            deezerArtist = "Jürgen Marcus",
            deezerAlbum = "Das Beste mit Liebe"
        ),
        "00037" to HitsterCard(
            hitsterId = "00037",
            title = "Manic Monday",
            artist = "The Bangles",
            year = 1986,
            deezerId = 608749,
            deezerTitle = "Manic Monday",
            deezerArtist = "The Bangles",
            deezerAlbum = "The Essential Bangles"
        ),
        "00038" to HitsterCard(
            hitsterId = "00038",
            title = "Paradise By The Dashboard Light",
            artist = "Meat Loaf",
            year = 1978,
            deezerId = 125468092,
            deezerTitle = "Paradise By the Dashboard Light",
            deezerArtist = "Meat Loaf",
            deezerAlbum = "Bat Out Of Hell"
        ),
        "00039" to HitsterCard(
            hitsterId = "00039",
            title = "Never Ending Story",
            artist = "Limahl",
            year = 1984,
            deezerId = 3518231,
            deezerTitle = "Never Ending Story",
            deezerArtist = "Limahl",
            deezerAlbum = "The Very Best Of Kajagoogoo"
        ),
        "00040" to HitsterCard(
            hitsterId = "00040",
            title = "Don't Call Me Up",
            artist = "Mabel",
            year = 2019,
            deezerId = 616963582,
            deezerTitle = "Don't Call Me Up",
            deezerArtist = "Mabel",
            deezerAlbum = "Ivy To Roses (Mixtape)"
        ),
        "00041" to HitsterCard(
            hitsterId = "00041",
            title = "Under The Bridge",
            artist = "Red Hot Chili Peppers",
            year = 1992,
            deezerId = 785176,
            deezerTitle = "Under the Bridge",
            deezerArtist = "Red Hot Chili Peppers",
            deezerAlbum = "Greatest Hits"
        ),
        "00042" to HitsterCard(
            hitsterId = "00042",
            title = "König von Deutschland",
            artist = "Rio Reiser",
            year = 1986,
            deezerId = 869747,
            deezerTitle = "König von Deutschland",
            deezerArtist = "Rio Reiser",
            deezerAlbum = "König von Deutschland - Das Beste von Rio Reiser"
        ),
        "00043" to HitsterCard(
            hitsterId = "00043",
            title = "Upside Down",
            artist = "Diana Ross",
            year = 1980,
            deezerId = 4677572,
            deezerTitle = "Upside Down",
            deezerArtist = "Diana Ross",
            deezerAlbum = "Diana"
        ),
        "00044" to HitsterCard(
            hitsterId = "00044",
            title = "Don't Speak",
            artist = "No Doubt",
            year = 1996,
            deezerId = 24246391,
            deezerTitle = "Don't Speak",
            deezerArtist = "No Doubt",
            deezerAlbum = "The Singles Collection"
        ),
        "00045" to HitsterCard(
            hitsterId = "00045",
            title = "(I Can't Get No) Satisfaction",
            artist = "The Rolling Stones",
            year = 1965,
            deezerId = 815051842,
            deezerTitle = "(I Can't Get No) Satisfaction",
            deezerArtist = "The Rolling Stones",
            deezerAlbum = "Live On The Ed Sullivan Show (Live)"
        ),
        "00046" to HitsterCard(
            hitsterId = "00046",
            title = "Dilemma",
            artist = "Nelly (feat. Kelly Rowland)",
            year = 2002,
            deezerId = 2447443,
            deezerTitle = "Dilemma",
            deezerArtist = "Nelly",
            deezerAlbum = "Nellyville"
        ),
        "00047" to HitsterCard(
            hitsterId = "00047",
            title = "Lola",
            artist = "The Kinks",
            year = 1970,
            deezerId = 2231539207,
            deezerTitle = "Lola",
            deezerArtist = "The Kinks",
            deezerAlbum = "Federal Capital Paranoia Blues"
        ),
        "00048" to HitsterCard(
            hitsterId = "00048",
            title = "Captain Jack",
            artist = "Captain Jack",
            year = 1995,
            deezerId = 61891432,
            deezerTitle = "Captain Jack (Short Mix)",
            deezerArtist = "Captain Jack",
            deezerAlbum = "The Mission"
        ),
        "00049" to HitsterCard(
            hitsterId = "00049",
            title = "True Colors",
            artist = "Cyndi Lauper",
            year = 1986,
            deezerId = 6067352,
            deezerTitle = "True Colors",
            deezerArtist = "Cyndi Lauper",
            deezerAlbum = "True Colors: The Best Of Cyndi Lauper"
        ),
        "00050" to HitsterCard(
            hitsterId = "00050",
            title = "Hey Ya!",
            artist = "Outkast",
            year = 2003,
            deezerId = 628266,
            deezerTitle = "Hey Ya!",
            deezerArtist = "Outkast",
            deezerAlbum = "Speakerboxxx/The Love Below"
        ),
        "00051" to HitsterCard(
            hitsterId = "00051",
            title = "Guajira Guantanamera",
            artist = "Joseíto Fernández",
            year = 1929,
            deezerId = 5214826,
            deezerTitle = "Guajira Guantanamera",
            deezerArtist = "Joseíto Fernández / La Calandria",
            deezerAlbum = "Great Cuban Songwriters"
        ),
        "00052" to HitsterCard(
            hitsterId = "00052",
            title = "La Cintura",
            artist = "Álvaro Soler",
            year = 2018,
            deezerId = 675340022,
            deezerTitle = "La Cintura",
            deezerArtist = "Alvaro Soler",
            deezerAlbum = "Mar De Colores (Versión Extendida)"
        ),
        "00053" to HitsterCard(
            hitsterId = "00053",
            title = "Sick and Tired",
            artist = "Anastacia",
            year = 2004,
            deezerId = 111772878,
            deezerTitle = "Sick and Tired",
            deezerArtist = "Anastacia",
            deezerAlbum = "Ultimate Collection"
        ),
        "00054" to HitsterCard(
            hitsterId = "00054",
            title = "If You Leave Me Now",
            artist = "Chicago",
            year = 1976,
            deezerId = 3616306,
            deezerTitle = "If You Leave Me Now",
            deezerArtist = "Chicago",
            deezerAlbum = "The Very Best of Chicago: Only the Beginning"
        ),
        "00055" to HitsterCard(
            hitsterId = "00055",
            title = "Siebzehn Jahr, blondes Haar",
            artist = "Udo Jürgens",
            year = 1965,
            deezerId = 4253142,
            deezerTitle = "Siebzehn Jahr, blondes Haar",
            deezerArtist = "Udo Jürgens",
            deezerAlbum = "Best Of"
        ),
        "00056" to HitsterCard(
            hitsterId = "00056",
            title = "Murder On The Dancefloor",
            artist = "Sophie Ellis-Bextor",
            year = 2001,
            deezerId = 4181750,
            deezerTitle = "Murder On The Dancefloor",
            deezerArtist = "Sophie Ellis-Bextor",
            deezerAlbum = "Read My Lips (Deluxe Version)"
        ),
        "00057" to HitsterCard(
            hitsterId = "00057",
            title = "Sing Hallelujah!",
            artist = "Dr. Alban",
            year = 1993,
            deezerId = 1687250217,
            deezerTitle = "Sing Hallelujah!",
            deezerArtist = "Dr. Alban",
            deezerAlbum = "One Love"
        ),
        "00058" to HitsterCard(
            hitsterId = "00058",
            title = "Set Fire To The Rain",
            artist = "Adele",
            year = 2011,
            deezerId = 8086130,
            deezerTitle = "Set Fire to the Rain",
            deezerArtist = "Adele",
            deezerAlbum = "21"
        ),
        "00059" to HitsterCard(
            hitsterId = "00059",
            title = "Herz an Herz",
            artist = "Blümchen",
            year = 1996,
            deezerId = 2888227,
            deezerTitle = "Herz an Herz",
            deezerArtist = "Blümchen",
            deezerAlbum = "Für immer und ewig"
        ),
        "00060" to HitsterCard(
            hitsterId = "00060",
            title = "Tage wie diese",
            artist = "Die Toten Hosen",
            year = 2012,
            deezerId = 142393383,
            deezerTitle = "Tage wie diese",
            deezerArtist = "Die Toten Hosen",
            deezerAlbum = "Tage wie diese"
        ),
        "00061" to HitsterCard(
            hitsterId = "00061",
            title = "Sweet Caroline",
            artist = "Neil Diamond",
            year = 1969,
            deezerId = 145434430,
            deezerTitle = "Sweet Caroline",
            deezerArtist = "Neil Diamond",
            deezerAlbum = "50th Anniversary Collection"
        ),
        "00062" to HitsterCard(
            hitsterId = "00062",
            title = "Do Wah Diddy Diddy",
            artist = "Manfred Mann",
            year = 1964,
            deezerId = 1930318867,
            deezerTitle = "Do Wah Diddy Diddy (2007 Remaster)",
            deezerArtist = "Manfred Mann",
            deezerAlbum = "Down the Road Apiece - the Recordings 1963-1966"
        ),
        "00063" to HitsterCard(
            hitsterId = "00063",
            title = "Über sieben Brücken musst du gehen",
            artist = "Karat",
            year = 1978,
            deezerId = 1135923432,
            deezerTitle = "Über sieben Brücken musst du gehen Live",
            deezerArtist = "Karat",
            deezerAlbum = "PETER MAFFAY UND..."
        ),
        "00064" to HitsterCard(
            hitsterId = "00064",
            title = "Don't Leave Me This Way",
            artist = "Thelma Houston",
            year = 1976,
            deezerId = 3067260,
            deezerTitle = "Don't Leave Me This Way",
            deezerArtist = "Thelma Houston",
            deezerAlbum = "The Best Of"
        ),
        "00065" to HitsterCard(
            hitsterId = "00065",
            title = "Can't Hold Us",
            artist = "Macklemore & Ryan Lewis (feat. Ray Dalton)",
            year = 2011,
            deezerId = 61424044,
            deezerTitle = "Can't Hold Us (feat. Ray Dalton)",
            deezerArtist = "Macklemore",
            deezerAlbum = "The Heist"
        ),
        "00066" to HitsterCard(
            hitsterId = "00066",
            title = "Do You Know? (The Ping Pong Song)",
            artist = "Enrique Iglesias",
            year = 2007,
            deezerId = 2604913,
            deezerTitle = "Do You Know? (The Ping Pong Song)",
            deezerArtist = "Enrique Iglesias",
            deezerAlbum = "Greatest Hits"
        ),
        "00067" to HitsterCard(
            hitsterId = "00067",
            title = "Warum",
            artist = "Tic Tac Toe",
            year = 1997,
            deezerId = 62379124,
            deezerTitle = "Warum?",
            deezerArtist = "Tic Tac Toe",
            deezerAlbum = "Klappe die 2te"
        ),
        "00068" to HitsterCard(
            hitsterId = "00068",
            title = "Gimme Hope Jo'anna",
            artist = "Eddy Grant",
            year = 1988,
            deezerId = 3510639391,
            deezerTitle = "Gimme Hope Jo'anna",
            deezerArtist = "Eddy Grant",
            deezerAlbum = "The Dave Cash Collection: Gimmie Eddy"
        ),
        "00069" to HitsterCard(
            hitsterId = "00069",
            title = "Wind of Change",
            artist = "Scorpions",
            year = 1991,
            deezerId = 927900,
            deezerTitle = "Wind Of Change",
            deezerArtist = "Scorpions",
            deezerAlbum = "Crazy World"
        ),
        "00070" to HitsterCard(
            hitsterId = "00070",
            title = "Le Freak",
            artist = "Chic",
            year = 1978,
            deezerId = 72060062,
            deezerTitle = "Le Freak",
            deezerArtist = "Chic",
            deezerAlbum = "The Studio Album Collection 1977-1992"
        ),
        "00071" to HitsterCard(
            hitsterId = "00071",
            title = "Haus am See",
            artist = "Peter Fox",
            year = 2008,
            deezerId = 2200206,
            deezerTitle = "Haus am See",
            deezerArtist = "Peter Fox",
            deezerAlbum = "Stadtaffe"
        ),
        "00072" to HitsterCard(
            hitsterId = "00072",
            title = "Poker Face",
            artist = "Lady Gaga",
            year = 2008,
            deezerId = 2603558,
            deezerTitle = "Poker Face",
            deezerArtist = "Lady Gaga",
            deezerAlbum = "The Fame"
        ),
        "00073" to HitsterCard(
            hitsterId = "00073",
            title = "It's A Heartache",
            artist = "Bonnie Tyler",
            year = 1977,
            deezerId = 131593760,
            deezerTitle = "It's a Heartache",
            deezerArtist = "Bonnie Tyler",
            deezerAlbum = "It's a Heartache"
        ),
        "00074" to HitsterCard(
            hitsterId = "00074",
            title = "Johnny B. Goode",
            artist = "Chuck Berry",
            year = 1958,
            deezerId = 2689636,
            deezerTitle = "Johnny B. Goode",
            deezerArtist = "Chuck Berry",
            deezerAlbum = "Chuck Berry"
        ),
        "00075" to HitsterCard(
            hitsterId = "00075",
            title = "White Wedding",
            artist = "Billy Idol",
            year = 1982,
            deezerId = 947711032,
            deezerTitle = "White Wedding",
            deezerArtist = "Billy Idol",
            deezerAlbum = "Live in Europe '90 (Live)"
        ),
        "00076" to HitsterCard(
            hitsterId = "00076",
            title = "La Donna È Mobile (Rigoletto)",
            artist = "Enrico Caruso",
            year = 1908,
            deezerId = 15468696,
            deezerTitle = "La donna è mobile (Rigoletto)",
            deezerArtist = "Enrico Caruso",
            deezerAlbum = "La donna è mobile"
        ),
        "00077" to HitsterCard(
            hitsterId = "00077",
            title = "Without You",
            artist = "Avicii",
            year = 2017,
            deezerId = 393460732,
            deezerTitle = "Without You",
            deezerArtist = "Avicii",
            deezerAlbum = "AVĪCI (01)"
        ),
        "00078" to HitsterCard(
            hitsterId = "00078",
            title = "Volare (Nel Blu Di Pinto Di Blu)",
            artist = "Gipsy Kings",
            year = 1989,
            deezerId = 958716372,
            deezerTitle = "Volare (Nel Blu di Pinto di Blu)",
            deezerArtist = "Gipsy Kings",
            deezerAlbum = "The Essential Gipsy Kings"
        ),
        "00079" to HitsterCard(
            hitsterId = "00079",
            title = "Ça Plane Pour Moi",
            artist = "Plastic Bertrand",
            year = 1977,
            deezerId = 12216125,
            deezerTitle = "Ca Plane Pour Moi",
            deezerArtist = "Plastic Bertrand",
            deezerAlbum = "L'Essential"
        ),
        "00080" to HitsterCard(
            hitsterId = "00080",
            title = "Born To Be Alive",
            artist = "Patrick Hernandez",
            year = 1978,
            deezerId = 67396065,
            deezerTitle = "Born To Be Alive",
            deezerArtist = "Patrick Hernandez",
            deezerAlbum = "Wog Boy 2 Original Soundtrack"
        ),
        "00081" to HitsterCard(
            hitsterId = "00081",
            title = "Whole Lotta Love",
            artist = "Led Zeppelin",
            year = 1969,
            deezerId = 2752768171,
            deezerTitle = "Whole lotta love",
            deezerArtist = "Led Zeppelin",
            deezerAlbum = "Ten songs for you"
        ),
        "00082" to HitsterCard(
            hitsterId = "00082",
            title = "Ein Kompliment",
            artist = "Sportfreunde Stiller",
            year = 2002,
            deezerId = 1791078487,
            deezerTitle = "Ein Kompliment",
            deezerArtist = "Sportfreunde Stiller",
            deezerAlbum = "Die gute Seite (Die lange Seite)"
        ),
        "00083" to HitsterCard(
            hitsterId = "00083",
            title = "In The Mood",
            artist = "Glenn Miller",
            year = 1939,
            deezerId = 570273,
            deezerTitle = "In The Mood",
            deezerArtist = "Glenn Miller",
            deezerAlbum = "The Essential Glenn Miller"
        ),
        "00084" to HitsterCard(
            hitsterId = "00084",
            title = "Wahnsinn",
            artist = "Wolfgang Petry",
            year = 1983,
            deezerId = 120358320,
            deezerTitle = "Wahnsinn",
            deezerArtist = "Wolfgang Petry",
            deezerAlbum = "40 Jahre - 40 Hits"
        ),
        "00085" to HitsterCard(
            hitsterId = "00085",
            title = "Counting Stars",
            artist = "OneRepublic",
            year = 2013,
            deezerId = 78527795,
            deezerTitle = "Counting Stars",
            deezerArtist = "OneRepublic",
            deezerAlbum = "Native"
        ),
        "00086" to HitsterCard(
            hitsterId = "00086",
            title = "Dragostea Din Tei",
            artist = "O-Zone",
            year = 2003,
            deezerId = 1411240392,
            deezerTitle = "Dragostea din tei",
            deezerArtist = "O-Zone",
            deezerAlbum = "DiscO-Zone"
        ),
        "00087" to HitsterCard(
            hitsterId = "00087",
            title = "Everybody Hurts",
            artist = "R.E.M.",
            year = 1992,
            deezerId = 121921372,
            deezerTitle = "Everybody Hurts",
            deezerArtist = "R.E.M.",
            deezerAlbum = "Automatic For The People"
        ),
        "00088" to HitsterCard(
            hitsterId = "00088",
            title = "Just Give Me A Reason",
            artist = "Pink (feat. Nate Ruess)",
            year = 2013,
            deezerId = 110931632,
            deezerTitle = "Just Give Me a Reason",
            deezerArtist = "Jason Chen & Megan Nicole",
            deezerAlbum = "Just Give Me a Reason (originally by P!nk feat. Nate Ruess)"
        ),
        "00089" to HitsterCard(
            hitsterId = "00089",
            title = "Joyride",
            artist = "Roxette",
            year = 1991,
            deezerId = 1523248472,
            deezerTitle = "Joyride",
            deezerArtist = "Roxette",
            deezerAlbum = "Joyride 30th Anniversary Edition"
        ),
        "00090" to HitsterCard(
            hitsterId = "00090",
            title = "(We're Gonna) Rock Around The Clock",
            artist = "Bill Haley & His Comets",
            year = 1954,
            deezerId = 602444242,
            deezerTitle = "(We're Gonna) Rock Around The Clock",
            deezerArtist = "Bill Haley & His Comets",
            deezerAlbum = "Rock Around The Clock"
        ),
        "00091" to HitsterCard(
            hitsterId = "00091",
            title = "Bette Davis Eyes",
            artist = "Kim Carnes",
            year = 1981,
            deezerId = 3153065,
            deezerTitle = "Bette Davis Eyes",
            deezerArtist = "Kim Carnes",
            deezerAlbum = "Gypsy Honeymoon: The Best Of Kim Carnes"
        ),
        "00092" to HitsterCard(
            hitsterId = "00092",
            title = "Lambada",
            artist = "Kaoma",
            year = 1989,
            deezerId = 2364224845,
            deezerTitle = "Lambada",
            deezerArtist = "Kaoma",
            deezerAlbum = "Movida Latina 3"
        ),
        "00093" to HitsterCard(
            hitsterId = "00093",
            title = "Ain't Nobody",
            artist = "Rufus (feat. Chaka Khan)",
            year = 1983,
            deezerId = 4094693,
            deezerTitle = "Ain't Nobody",
            deezerArtist = "Rufus",
            deezerAlbum = "Stompin' At The Savoy"
        ),
        "00094" to HitsterCard(
            hitsterId = "00094",
            title = "Have You Ever Seen the Rain?",
            artist = "Creedence Clearwater Revival",
            year = 1971,
            deezerId = 883644,
            deezerTitle = "Have You Ever Seen The Rain",
            deezerArtist = "Creedence Clearwater Revival",
            deezerAlbum = "Creedence Clearwater Revival - Best Of"
        ),
        "00095" to HitsterCard(
            hitsterId = "00095",
            title = "(I've Had) The Time Of My Life",
            artist = "Bill Medley & Jennifer Warnes",
            year = 1987,
            deezerId = 13128250,
            deezerTitle = "(I've Had) The Time Of My Life (From \"Dirty Dancing\" Soundtrack)",
            deezerArtist = "Bill Medley",
            deezerAlbum = "Dirty Dancing (Original Motion Picture Soundtrack)"
        ),
        "00096" to HitsterCard(
            hitsterId = "00096",
            title = "Più Bella Cosa",
            artist = "Eros Ramazzotti",
            year = 1996,
            deezerId = 604840,
            deezerTitle = "Più bella cosa",
            deezerArtist = "Eros Ramazzotti",
            deezerAlbum = "Eros - Best Of"
        ),
        "00097" to HitsterCard(
            hitsterId = "00097",
            title = "No Son of Mine",
            artist = "Genesis",
            year = 1991,
            deezerId = 14638202,
            deezerTitle = "No Son of Mine",
            deezerArtist = "Genesis",
            deezerAlbum = "Turn It on Again: The Hits"
        ),
        "00098" to HitsterCard(
            hitsterId = "00098",
            title = "Happy",
            artist = "Pharrell Williams",
            year = 2013,
            deezerId = 701326562,
            deezerTitle = "Happy (From \"Despicable Me 2\")",
            deezerArtist = "Pharrell Williams",
            deezerAlbum = "G I R L"
        ),
        "00099" to HitsterCard(
            hitsterId = "00099",
            title = "Wonderwall",
            artist = "Oasis",
            year = 1995,
            deezerId = 985745702,
            deezerTitle = "Wonderwall",
            deezerArtist = "Oasis",
            deezerAlbum = "(What's The Story) Morning Glory?"
        ),
        "00100" to HitsterCard(
            hitsterId = "00100",
            title = "You Can't Hurry Love",
            artist = "The Supremes",
            year = 1966,
            deezerId = 79621446,
            deezerTitle = "You Can't Hurry Love",
            deezerArtist = "The Supremes",
            deezerAlbum = "Supremes A Go Go"
        ),
        "00101" to HitsterCard(
            hitsterId = "00101",
            title = "Gold Digger",
            artist = "Kanye West (feat. Jamie Foxx)",
            year = 2005,
            deezerId = 1184309,
            deezerTitle = "Gold Digger",
            deezerArtist = "Kanye West",
            deezerAlbum = "Late Registration"
        ),
        "00102" to HitsterCard(
            hitsterId = "00102",
            title = "Ich will Spaß",
            artist = "Markus",
            year = 1982,
            deezerId = 663173602,
            deezerTitle = "Ich will Spaß",
            deezerArtist = "Markus",
            deezerAlbum = "Stereo (2 Originale)"
        ),
        "00103" to HitsterCard(
            hitsterId = "00103",
            title = "Lady (Hear Me Tonight)",
            artist = "Modjo",
            year = 2000,
            deezerId = 70179720,
            deezerTitle = "Lady (Hear Me Tonight)",
            deezerArtist = "Modjo",
            deezerAlbum = "Modjo (Remastered)"
        ),
        "00104" to HitsterCard(
            hitsterId = "00104",
            title = "She Drives Me Crazy",
            artist = "Fine Young Cannibals",
            year = 1988,
            deezerId = 1178372072,
            deezerTitle = "She Drives Me Crazy (Remastered)",
            deezerArtist = "Fine Young Cannibals",
            deezerAlbum = "The Raw & The Cooked (Remastered & Expanded)"
        ),
        "00105" to HitsterCard(
            hitsterId = "00105",
            title = "Heimweh (Dort wo die Blumen blüh'n)",
            artist = "Freddy Quinn",
            year = 1956,
            deezerId = 2308635,
            deezerTitle = "Heimweh (Dort wo die Blumen blüh'n)",
            deezerArtist = "Freddy Quinn",
            deezerAlbum = "Freddy, die Gitarre und das Meer"
        ),
        "00106" to HitsterCard(
            hitsterId = "00106",
            title = "Moskau",
            artist = "Dschinghis Khan",
            year = 1979,
            deezerId = 470543912,
            deezerTitle = "Moskau",
            deezerArtist = "Dschinghis Khan",
            deezerAlbum = "Moskau - Das Neue Best Of Album"
        ),
        "00107" to HitsterCard(
            hitsterId = "00107",
            title = "Cotton Eye Joe",
            artist = "Rednex",
            year = 1994,
            deezerId = 419139102,
            deezerTitle = "Cotton Eye Joe",
            deezerArtist = "Rednex",
            deezerAlbum = "Sex & Violins"
        ),
        "00108" to HitsterCard(
            hitsterId = "00108",
            title = "I Can Help",
            artist = "Billy Swan",
            year = 1974,
            deezerId = 15273580,
            deezerTitle = "I Can Help",
            deezerArtist = "Billy Swan",
            deezerAlbum = "The Best Of Billy Swan"
        ),
        "00109" to HitsterCard(
            hitsterId = "00109",
            title = "Dancing In The Dark",
            artist = "Bruce Springsteen",
            year = 1984,
            deezerId = 15586246,
            deezerTitle = "Dancing In the Dark",
            deezerArtist = "Bruce Springsteen",
            deezerAlbum = "Born In The U.S.A."
        ),
        "00110" to HitsterCard(
            hitsterId = "00110",
            title = "Day-O (The Banana Boat Song)",
            artist = "Harry Belafonte",
            year = 1956,
            deezerId = 16045403,
            deezerTitle = "Day-O (The Banana Boat Song)",
            deezerArtist = "Harry Belafonte",
            deezerAlbum = "Day-O"
        ),
        "00111" to HitsterCard(
            hitsterId = "00111",
            title = "Ich will 'nen Cowboy als Mann",
            artist = "Gitte Hænning",
            year = 1963,
            deezerId = 1408767732,
            deezerTitle = "Ich will 'nen Cowboy als Mann",
            deezerArtist = "Gitte Hænning",
            deezerAlbum = "Electrola… Das ist Musik! Gitte Hænning"
        ),
        "00112" to HitsterCard(
            hitsterId = "00112",
            title = "Break My Heart",
            artist = "Dua Lipa",
            year = 2020,
            deezerId = 910173472,
            deezerTitle = "Break My Heart",
            deezerArtist = "Dua Lipa",
            deezerAlbum = "Break My Heart"
        ),
        "00113" to HitsterCard(
            hitsterId = "00113",
            title = "Wrecking Ball",
            artist = "Miley Cyrus",
            year = 2013,
            deezerId = 71137166,
            deezerTitle = "Wrecking Ball",
            deezerArtist = "Miley Cyrus",
            deezerAlbum = "Bangerz (Deluxe Version)"
        ),
        "00114" to HitsterCard(
            hitsterId = "00114",
            title = "Jailhouse Rock",
            artist = "Elvis Presley",
            year = 1957,
            deezerId = 6596867,
            deezerTitle = "Jailhouse Rock",
            deezerArtist = "Elvis Presley",
            deezerAlbum = "The Essential Elvis Presley 3.0"
        ),
        "00115" to HitsterCard(
            hitsterId = "00115",
            title = "Adore You",
            artist = "Harry Styles",
            year = 2019,
            deezerId = 830336932,
            deezerTitle = "Adore You",
            deezerArtist = "Harry Styles",
            deezerAlbum = "Fine Line"
        ),
        "00116" to HitsterCard(
            hitsterId = "00116",
            title = "Good Golly, Miss Molly",
            artist = "Little Richard",
            year = 1958,
            deezerId = 73012063,
            deezerTitle = "Good Golly, Miss Molly (Remastered)",
            deezerArtist = "Little Richard",
            deezerAlbum = "100 Rock'n'Roll Hits (Remastered)"
        ),
        "00117" to HitsterCard(
            hitsterId = "00117",
            title = "The Ketchup Song (Aserejé)",
            artist = "Las Ketchup",
            year = 2002,
            deezerId = 476739462,
            deezerTitle = "The Ketchup Song (Aserejé) (Spanish Version)",
            deezerArtist = "Las Ketchup",
            deezerAlbum = "Aserejé (The Ketchup Song)"
        ),
        "00118" to HitsterCard(
            hitsterId = "00118",
            title = "Relax, Take It Easy",
            artist = "Mika",
            year = 2007,
            deezerId = 953606,
            deezerTitle = "Relax, Take It Easy",
            deezerArtist = "MIKA",
            deezerAlbum = "Life In Cartoon Motion (UK eDeluxe Album)"
        ),
        "00119" to HitsterCard(
            hitsterId = "00119",
            title = "I'm A Believer",
            artist = "The Monkees",
            year = 1966,
            deezerId = 764920,
            deezerTitle = "I'm a Believer (2006 Remaster)",
            deezerArtist = "The Monkees",
            deezerAlbum = "More of The Monkees (Deluxe Edition)"
        ),
        "00120" to HitsterCard(
            hitsterId = "00120",
            title = "Schuld war nur der Bossa Nova",
            artist = "Manuela",
            year = 1963,
            deezerId = 598445,
            deezerTitle = "Schuld war nur der Bossa Nova",
            deezerArtist = "Manuela",
            deezerAlbum = "Jive Manuela"
        ),
        "00121" to HitsterCard(
            hitsterId = "00121",
            title = "I Love It",
            artist = "Icona Pop (feat. Charli XCX)",
            year = 2012,
            deezerId = 69959333,
            deezerTitle = "I Love It (feat. Charli XCX)",
            deezerArtist = "Icona Pop",
            deezerAlbum = "THIS IS... ICONA POP"
        ),
        "00122" to HitsterCard(
            hitsterId = "00122",
            title = "World, Hold On (Children Of The Sky)",
            artist = "Bob Sinclar (feat. Steve Edwards)",
            year = 2006,
            deezerId = 2702194582,
            deezerTitle = "World, Hold On (Children Of The Sky) (Extended Club Mix)",
            deezerArtist = "Bob Sinclar",
            deezerAlbum = "World, Hold On (Children Of The Sky)- EP"
        ),
        "00123" to HitsterCard(
            hitsterId = "00123",
            title = "Born To Be Wild",
            artist = "Steppenwolf",
            year = 1968,
            deezerId = 540202122,
            deezerTitle = "Born To Be Wild",
            deezerArtist = "Steppenwolf",
            deezerAlbum = "Born To Be Wild (Best Of....)"
        ),
        "00124" to HitsterCard(
            hitsterId = "00124",
            title = "Valerie",
            artist = "Mark Ronson (feat. Amy Winehouse)",
            year = 2007,
            deezerId = 7923756,
            deezerTitle = "Valerie (feat. Amy Winehouse) (Version Revisited)",
            deezerArtist = "Mark Ronson",
            deezerAlbum = "Version"
        ),
        "00125" to HitsterCard(
            hitsterId = "00125",
            title = "(What A) Wonderful World",
            artist = "Sam Cooke",
            year = 1960,
            deezerId = 13205914,
            deezerTitle = "(What A) Wonderful World",
            deezerArtist = "Sam Cooke",
            deezerAlbum = "The Man Who Invented Soul"
        ),
        "00126" to HitsterCard(
            hitsterId = "00126",
            title = "Männer",
            artist = "Herbert Grönemeyer",
            year = 1984,
            deezerId = 3159913,
            deezerTitle = "Männer",
            deezerArtist = "Herbert Grönemeyer",
            deezerAlbum = "Was Muss Muss - Best Of"
        ),
        "00127" to HitsterCard(
            hitsterId = "00127",
            title = "You Get What You Give",
            artist = "New Radicals",
            year = 1998,
            deezerId = 2441438,
            deezerTitle = "You Get What You Give",
            deezerArtist = "New Radicals",
            deezerAlbum = "Maybe You've Been Brainwashed Too"
        ),
        "00128" to HitsterCard(
            hitsterId = "00128",
            title = "Mama",
            artist = "Heintje",
            year = 1968,
            deezerId = 456971872,
            deezerTitle = "Mama",
            deezerArtist = "Heintje",
            deezerAlbum = "Mama"
        ),
        "00129" to HitsterCard(
            hitsterId = "00129",
            title = "My Heart Will Go On",
            artist = "Céline Dion",
            year = 1997,
            deezerId = 14552280,
            deezerTitle = "My Heart Will Go On (Love Theme from \"Titanic\")",
            deezerArtist = "Céline Dion",
            deezerAlbum = "My Love Essential Collection"
        ),
        "00130" to HitsterCard(
            hitsterId = "00130",
            title = "Dancing On The Ceiling",
            artist = "Lionel Richie",
            year = 1986,
            deezerId = 905690,
            deezerTitle = "Dancing On The Ceiling",
            deezerArtist = "Lionel Richie",
            deezerAlbum = "Dancing On The Ceiling (Expanded Edition)"
        ),
        "00131" to HitsterCard(
            hitsterId = "00131",
            title = "Give It Up",
            artist = "KC And The Sunshine Band",
            year = 1982,
            deezerId = 541998,
            deezerTitle = "Give It Up",
            deezerArtist = "Kc & The Sunshine Band",
            deezerAlbum = "The Definitive 80's (eighties)"
        ),
        "00132" to HitsterCard(
            hitsterId = "00132",
            title = "Feel",
            artist = "Robbie Williams",
            year = 2002,
            deezerId = 3098217,
            deezerTitle = "Feel",
            deezerArtist = "Robbie Williams",
            deezerAlbum = "Escapology"
        ),
        "00133" to HitsterCard(
            hitsterId = "00133",
            title = "How You Remind Me",
            artist = "Nickelback",
            year = 2001,
            deezerId = 71828723,
            deezerTitle = "How You Remind Me",
            deezerArtist = "Nickelback",
            deezerAlbum = "The Best of Nickelback, Vol. 1"
        ),
        "00134" to HitsterCard(
            hitsterId = "00134",
            title = "Got My Mind Set On You",
            artist = "George Harrison",
            year = 1987,
            deezerId = 2141052707,
            deezerTitle = "Got My Mind Set On You",
            deezerArtist = "George Harrison",
            deezerAlbum = "Cloud Nine"
        ),
        "00135" to HitsterCard(
            hitsterId = "00135",
            title = "Verdammt, ich lieb' dich",
            artist = "Matthias Reim",
            year = 1990,
            deezerId = 111773080,
            deezerTitle = "Verdammt Ich lieb' dich",
            deezerArtist = "Matthias Reim",
            deezerAlbum = "Ich find' Schlager toll"
        ),
        "00136" to HitsterCard(
            hitsterId = "00136",
            title = "I Like To Move It",
            artist = "Reel 2 Real (feat. The Mad Stuntman)",
            year = 1993,
            deezerId = 524374752,
            deezerTitle = "I Like To Move It (feat. The Mad Stuntman) (Erick \"More\" Album Mix)",
            deezerArtist = "Reel 2 Real",
            deezerAlbum = "Move It!"
        ),
        "00137" to HitsterCard(
            hitsterId = "00137",
            title = "Don't You Want Me",
            artist = "The Human League",
            year = 1981,
            deezerId = 3092940,
            deezerTitle = "Don't You Want Me",
            deezerArtist = "The Human League",
            deezerAlbum = "Dare!"
        ),
        "00138" to HitsterCard(
            hitsterId = "00138",
            title = "Drei Uhr nachts",
            artist = "Mark Forster feat. Lea",
            year = 2021,
            deezerId = 1288053342,
            deezerTitle = "Drei Uhr Nachts",
            deezerArtist = "Mark Forster",
            deezerAlbum = "Drei Uhr Nachts"
        ),
        "00139" to HitsterCard(
            hitsterId = "00139",
            title = "Westerland",
            artist = "Die Ärzte",
            year = 1988,
            deezerId = 1095340122,
            deezerTitle = "Westerland",
            deezerArtist = "Die Ärzte",
            deezerAlbum = "Das Ist Nicht Die Ganze Wahrheit..."
        ),
        "00140" to HitsterCard(
            hitsterId = "00140",
            title = "Every Breath You Take",
            artist = "The Police",
            year = 1983,
            deezerId = 2525864,
            deezerTitle = "Every Breath You Take",
            deezerArtist = "The Police",
            deezerAlbum = "The Very Best Of Sting And The Police"
        ),
        "00141" to HitsterCard(
            hitsterId = "00141",
            title = "Bitter Sweet Symphony",
            artist = "The Verve",
            year = 1997,
            deezerId = 3138832,
            deezerTitle = "Bitter Sweet Symphony",
            deezerArtist = "The Verve",
            deezerAlbum = "Bitter Sweet Symphony"
        ),
        "00142" to HitsterCard(
            hitsterId = "00142",
            title = "Gettin' Jiggy Wit It",
            artist = "Will Smith",
            year = 1998,
            deezerId = 1038048,
            deezerTitle = "Gettin' Jiggy Wit It",
            deezerArtist = "Will Smith",
            deezerAlbum = "Big Willie Style"
        ),
        "00143" to HitsterCard(
            hitsterId = "00143",
            title = "Barbie Girl",
            artist = "Aqua",
            year = 1997,
            deezerId = 1115044,
            deezerTitle = "Barbie Girl",
            deezerArtist = "Aqua",
            deezerAlbum = "Aquarium"
        ),
        "00144" to HitsterCard(
            hitsterId = "00144",
            title = "Pack die Badehose ein",
            artist = "Cornelia Froboess",
            year = 1951,
            deezerId = 13437254,
            deezerTitle = "Pack Die Badehose Ein",
            deezerArtist = "Cornelia Froboess",
            deezerAlbum = "Cornelia Froboess: 50's Gold"
        ),
        "00145" to HitsterCard(
            hitsterId = "00145",
            title = "Torn",
            artist = "Natalie Imbruglia",
            year = 1997,
            deezerId = 992131,
            deezerTitle = "Torn",
            deezerArtist = "Natalie Imbruglia",
            deezerAlbum = "Left Of The Middle"
        ),
        "00146" to HitsterCard(
            hitsterId = "00146",
            title = "The Wanderer",
            artist = "Dion",
            year = 1961,
            deezerId = 3121437,
            deezerTitle = "The Wanderer",
            deezerArtist = "Dion",
            deezerAlbum = "The Best Of Dion & The Belmonts"
        ),
        "00147" to HitsterCard(
            hitsterId = "00147",
            title = "La Bamba",
            artist = "Ritchie Valens",
            year = 1958,
            deezerId = 104816990,
            deezerTitle = "La Bamba",
            deezerArtist = "Ritchie Valens",
            deezerAlbum = "Donna"
        ),
        "00148" to HitsterCard(
            hitsterId = "00148",
            title = "Toosie Slide",
            artist = "Drake",
            year = 2020,
            deezerId = 919708552,
            deezerTitle = "Toosie Slide",
            deezerArtist = "Drake",
            deezerAlbum = "Toosie Slide"
        ),
        "00149" to HitsterCard(
            hitsterId = "00149",
            title = "Drivers License",
            artist = "Olivia Rodrigo",
            year = 2021,
            deezerId = 1378342592,
            deezerTitle = "drivers license",
            deezerArtist = "Olivia Rodrigo",
            deezerAlbum = "SOUR"
        ),
        "00150" to HitsterCard(
            hitsterId = "00150",
            title = "Moves Like Jagger",
            artist = "Maroon 5 (feat. Christina Aguilera)",
            year = 2011,
            deezerId = 12724819,
            deezerTitle = "Moves Like Jagger (Studio Recording From \"The Voice\" Performance)",
            deezerArtist = "Maroon 5",
            deezerAlbum = "Hands All Over (Revised International Deluxe)"
        ),
        "00151" to HitsterCard(
            hitsterId = "00151",
            title = "Atemlos durch die Nacht",
            artist = "Helene Fischer",
            year = 2013,
            deezerId = 72761680,
            deezerTitle = "Atemlos durch die Nacht",
            deezerArtist = "Helene Fischer",
            deezerAlbum = "Atemlos durch die Nacht (The Radio Mixes)"
        ),
        "00152" to HitsterCard(
            hitsterId = "00152",
            title = "Stop",
            artist = "Spice Girls",
            year = 1998,
            deezerId = 3134156,
            deezerTitle = "Stop",
            deezerArtist = "Spice Girls",
            deezerAlbum = "Spiceworld"
        ),
        "00153" to HitsterCard(
            hitsterId = "00153",
            title = "If You Had My Love",
            artist = "Jennifer Lopez",
            year = 1999,
            deezerId = 15475926,
            deezerTitle = "If You Had My Love",
            deezerArtist = "Jennifer Lopez",
            deezerAlbum = "On The 6"
        ),
        "00154" to HitsterCard(
            hitsterId = "00154",
            title = "More Than A Feeling",
            artist = "Boston",
            year = 1976,
            deezerId = 1037414,
            deezerTitle = "More Than a Feeling",
            deezerArtist = "Boston",
            deezerAlbum = "Greatest Hits"
        ),
        "00155" to HitsterCard(
            hitsterId = "00155",
            title = "Disco Inferno",
            artist = "The Trammps",
            year = 1976,
            deezerId = 721611,
            deezerTitle = "Disco Inferno",
            deezerArtist = "The Trammps",
            deezerAlbum = "Disco Inferno"
        ),
        "00156" to HitsterCard(
            hitsterId = "00156",
            title = "Someone You Loved",
            artist = "Lewis Capaldi",
            year = 2018,
            deezerId = 582143242,
            deezerTitle = "Someone You Loved",
            deezerArtist = "Lewis Capaldi",
            deezerAlbum = "Breach"
        ),
        "00157" to HitsterCard(
            hitsterId = "00157",
            title = "Mief! (Nimm mich jetzt, auch wenn ich stinke)",
            artist = "Die Doofen",
            year = 1995,
            deezerId = 1060193,
            deezerTitle = "MIEF! (Nimm mich jetzt, auch wenn ich stinke) Video Version",
            deezerArtist = "Die Doofen",
            deezerAlbum = "Lieder, die die Welt nicht braucht"
        ),
        "00158" to HitsterCard(
            hitsterId = "00158",
            title = "Sweet Child O' Mine",
            artist = "Guns N' Roses",
            year = 1988,
            deezerId = 518458172,
            deezerTitle = "Sweet Child O' Mine",
            deezerArtist = "Guns N' Roses",
            deezerAlbum = "Appetite For Destruction (Super Deluxe Edition)"
        ),
        "00159" to HitsterCard(
            hitsterId = "00159",
            title = "Your Song",
            artist = "Elton John",
            year = 1970,
            deezerId = 880181,
            deezerTitle = "Your Song",
            deezerArtist = "Elton John",
            deezerAlbum = "Elton John"
        ),
        "00160" to HitsterCard(
            hitsterId = "00160",
            title = "Congratulations",
            artist = "Cliff Richard",
            year = 1968,
            deezerId = 3124130,
            deezerTitle = "Congratulations",
            deezerArtist = "Cliff Richard",
            deezerAlbum = "40 Golden Greats"
        ),
        "00161" to HitsterCard(
            hitsterId = "00161",
            title = "You're So Vain",
            artist = "Carly Simon",
            year = 1972,
            deezerId = 766109,
            deezerTitle = "You're So Vain",
            deezerArtist = "Carly Simon",
            deezerAlbum = "The Best of Carly Simon"
        ),
        "00162" to HitsterCard(
            hitsterId = "00162",
            title = "Easy",
            artist = "Commodores",
            year = 1977,
            deezerId = 2177862,
            deezerTitle = "Easy",
            deezerArtist = "Commodores",
            deezerAlbum = "Back To Front"
        ),
        "00163" to HitsterCard(
            hitsterId = "00163",
            title = "Forever Young",
            artist = "Alphaville",
            year = 1984,
            deezerId = 698274,
            deezerTitle = "Forever Young",
            deezerArtist = "Alphaville",
            deezerAlbum = "Forever Young"
        ),
        "00164" to HitsterCard(
            hitsterId = "00164",
            title = "Maneater",
            artist = "Daryl Hall & John Oates",
            year = 1982,
            deezerId = 569561,
            deezerTitle = "Maneater",
            deezerArtist = "Daryl Hall & John Oates",
            deezerAlbum = "The Essential Daryl Hall & John Oates"
        ),
        "00165" to HitsterCard(
            hitsterId = "00165",
            title = "Rock Me Amadeus",
            artist = "Falco",
            year = 1985,
            deezerId = 542016,
            deezerTitle = "Rock Me Amadeus",
            deezerArtist = "Falco",
            deezerAlbum = "The Definitive 80's (eighties)"
        ),
        "00166" to HitsterCard(
            hitsterId = "00166",
            title = "I Got You Babe",
            artist = "Sonny & Cher",
            year = 1965,
            deezerId = 735788,
            deezerTitle = "I Got You Babe",
            deezerArtist = "Sonny & Cher",
            deezerAlbum = "The Two Of Us"
        ),
        "00167" to HitsterCard(
            hitsterId = "00167",
            title = "Junge",
            artist = "Die Ärzte",
            year = 2007,
            deezerId = 1006111382,
            deezerTitle = "Junge",
            deezerArtist = "Die Ärzte",
            deezerAlbum = "Jazz ist anders"
        ),
        "00168" to HitsterCard(
            hitsterId = "00168",
            title = "Everywhere",
            artist = "Fleetwood Mac",
            year = 1987,
            deezerId = 664444,
            deezerTitle = "Everywhere",
            deezerArtist = "Fleetwood Mac",
            deezerAlbum = "Greatest Hits"
        ),
        "00169" to HitsterCard(
            hitsterId = "00169",
            title = "Zombie",
            artist = "The Cranberries",
            year = 1994,
            deezerId = 952788,
            deezerTitle = "Zombie",
            deezerArtist = "The Cranberries",
            deezerAlbum = "Girls And Guitars"
        ),
        "00170" to HitsterCard(
            hitsterId = "00170",
            title = "Barbra Streisand",
            artist = "Duck Sauce",
            year = 2010,
            deezerId = 3157769351,
            deezerTitle = "Barbra Streisand",
            deezerArtist = "Duck Sauce",
            deezerAlbum = "Quack"
        ),
        "00171" to HitsterCard(
            hitsterId = "00171",
            title = "No One",
            artist = "Alicia Keys",
            year = 2007,
            deezerId = 2466262,
            deezerTitle = "No One",
            deezerArtist = "Alicia Keys",
            deezerAlbum = "As I Am (Expanded Edition)"
        ),
        "00172" to HitsterCard(
            hitsterId = "00172",
            title = "I Knew You Were Waiting (For Me)",
            artist = "George Michael & Aretha Franklin",
            year = 1987,
            deezerId = 142787302,
            deezerTitle = "I Knew You Were Waiting (For Me)",
            deezerArtist = "George Michael",
            deezerAlbum = "Aretha (Expanded Edition)"
        ),
        "00173" to HitsterCard(
            hitsterId = "00173",
            title = "December, 1963 (Oh, What A Night)",
            artist = "Frankie Valli & The Four Seasons",
            year = 1975,
            deezerId = 79572018,
            deezerTitle = "December, 1963 (Oh, What a Night)",
            deezerArtist = "Frankie Valli & The Four Seasons",
            deezerAlbum = "Jersey Boys: Music from the Motion Picture and Broadway Musical"
        ),
        "00174" to HitsterCard(
            hitsterId = "00174",
            title = "4 Minutes",
            artist = "Madonna (feat. Justin Timberlake & Timbaland)",
            year = 2008,
            deezerId = 794020,
            deezerTitle = "4 Minutes (feat. Justin Timberlake and Timbaland)",
            deezerArtist = "Madonna",
            deezerAlbum = "Hard Candy (Deluxe Digital)"
        ),
        "00175" to HitsterCard(
            hitsterId = "00175",
            title = "Autobahn",
            artist = "Kraftwerk",
            year = 1974,
            deezerId = 1006803512,
            deezerTitle = "Autobahn",
            deezerArtist = "Kraftwerk",
            deezerAlbum = "3-D Der Katalog (German Version)"
        ),
        "00176" to HitsterCard(
            hitsterId = "00176",
            title = "She's A Lady",
            artist = "Tom Jones",
            year = 1971,
            deezerId = 549298332,
            deezerTitle = "She's A Lady",
            deezerArtist = "Tom Jones",
            deezerAlbum = "The Best Of Tom Jones - 20th Century Masters: The Millennium Collection"
        ),
        "00177" to HitsterCard(
            hitsterId = "00177",
            title = "Señorita",
            artist = "Shawn Mendes & Camila Cabello",
            year = 2019,
            deezerId = 698905582,
            deezerTitle = "Señorita",
            deezerArtist = "Shawn Mendes",
            deezerAlbum = "Señorita"
        ),
        "00178" to HitsterCard(
            hitsterId = "00178",
            title = "Killing Me Softly With His Song",
            artist = "Fugees",
            year = 1996,
            deezerId = 869548,
            deezerTitle = "Killing Me Softly With His Song",
            deezerArtist = "Fugees",
            deezerAlbum = "Greatest Hits"
        ),
        "00179" to HitsterCard(
            hitsterId = "00179",
            title = "Oops!... I Did It Again",
            artist = "Britney Spears",
            year = 2000,
            deezerId = 13142617,
            deezerTitle = "Oops!...I Did It Again",
            deezerArtist = "Britney Spears",
            deezerAlbum = "Oops!... I Did It Again"
        ),
        "00180" to HitsterCard(
            hitsterId = "00180",
            title = "Nur noch kurz die Welt retten",
            artist = "Tim Bendzko",
            year = 2011,
            deezerId = 12000049,
            deezerTitle = "Nur noch kurz die Welt retten",
            deezerArtist = "Tim Bendzko",
            deezerAlbum = "Nur noch kurz die Welt retten"
        ),
        "00181" to HitsterCard(
            hitsterId = "00181",
            title = "Whatcha Say",
            artist = "Jason Derulo",
            year = 2009,
            deezerId = 5420471,
            deezerTitle = "Whatcha Say",
            deezerArtist = "Jason Derulo",
            deezerAlbum = "Jason Derulo (International)"
        ),
        "00182" to HitsterCard(
            hitsterId = "00182",
            title = "Abenteuerland",
            artist = "Pur",
            year = 1995,
            deezerId = 99127316,
            deezerTitle = "Abenteuerland",
            deezerArtist = "Pur",
            deezerAlbum = "Hits Pur - 20 Jahre Eine Band (Fan Edition)"
        ),
        "00183" to HitsterCard(
            hitsterId = "00183",
            title = "Talk",
            artist = "Coldplay",
            year = 2005,
            deezerId = 3106506,
            deezerTitle = "Talk",
            deezerArtist = "Coldplay",
            deezerAlbum = "X&Y"
        ),
        "00184" to HitsterCard(
            hitsterId = "00184",
            title = "Somebody That I Used To Know",
            artist = "Gotye (feat. Kimbra)",
            year = 2011,
            deezerId = 14539929,
            deezerTitle = "Somebody That I Used To Know",
            deezerArtist = "Gotye",
            deezerAlbum = "Making Mirrors"
        ),
        "00185" to HitsterCard(
            hitsterId = "00185",
            title = "Fast Car",
            artist = "Tracy Chapman",
            year = 1988,
            deezerId = 2271563,
            deezerTitle = "Fast Car",
            deezerArtist = "Tracy Chapman",
            deezerAlbum = "Tracy Chapman"
        ),
        "00186" to HitsterCard(
            hitsterId = "00186",
            title = "The Living Years",
            artist = "Mike & The Mechanics",
            year = 1988,
            deezerId = 2598565352,
            deezerTitle = "The Living Years",
            deezerArtist = "Mike + The Mechanics",
            deezerAlbum = "Out Of The Blue"
        ),
        "00187" to HitsterCard(
            hitsterId = "00187",
            title = "Ab in den Süden",
            artist = "Buddy",
            year = 2003,
            deezerId = 677931742,
            deezerTitle = "Ab in den Süden (Neuaufnahme)",
            deezerArtist = "Buddy Poke",
            deezerAlbum = "Party Hits"
        ),
        "00188" to HitsterCard(
            hitsterId = "00188",
            title = "The Boy Is Mine",
            artist = "Brandy (feat. Monica)",
            year = 1998,
            deezerId = 662355,
            deezerTitle = "The Boy Is Mine",
            deezerArtist = "Brandy",
            deezerAlbum = "Never Say Never"
        ),
        "00189" to HitsterCard(
            hitsterId = "00189",
            title = "All I Have To Do Is Dream",
            artist = "The Everly Brothers",
            year = 1958,
            deezerId = 74494813,
            deezerTitle = "All I Have to Do Is Dream",
            deezerArtist = "The Everly Brothers",
            deezerAlbum = "The Very Best of The Everly Brothers"
        ),
        "00190" to HitsterCard(
            hitsterId = "00190",
            title = "Surfin' U.S.A.",
            artist = "The Beach Boys",
            year = 1963,
            deezerId = 2958899471,
            deezerTitle = "Surfin' U.S.A.",
            deezerArtist = "The Beach Boys",
            deezerAlbum = "Los Años 60"
        ),
        "00191" to HitsterCard(
            hitsterId = "00191",
            title = "Whole Again",
            artist = "Atomic Kitten",
            year = 2001,
            deezerId = 3240483,
            deezerTitle = "Whole Again",
            deezerArtist = "Atomic Kitten",
            deezerAlbum = "Feels So Good"
        ),
        "00192" to HitsterCard(
            hitsterId = "00192",
            title = "Right Here Waiting",
            artist = "Richard Marx",
            year = 1989,
            deezerId = 3168937,
            deezerTitle = "Right Here Waiting",
            deezerArtist = "Richard Marx",
            deezerAlbum = "Repeat Offender"
        ),
        "00193" to HitsterCard(
            hitsterId = "00193",
            title = "Geboren um zu leben",
            artist = "Unheilig",
            year = 2010,
            deezerId = 75486189,
            deezerTitle = "Geboren um zu leben",
            deezerArtist = "Unheilig",
            deezerAlbum = "Alles hat seine Zeit - Best Of Unheilig 1999 - 2014"
        ),
        "00194" to HitsterCard(
            hitsterId = "00194",
            title = "Die Nacht von Freitag auf Montag",
            artist = "SDP",
            year = 2012,
            deezerId = 61394924,
            deezerTitle = "Die Nacht von Freitag auf Montag",
            deezerArtist = "SDP",
            deezerAlbum = "Die bekannteste unbekannte Band der Welt"
        ),
        "00195" to HitsterCard(
            hitsterId = "00195",
            title = "Fallin'",
            artist = "Alicia Keys",
            year = 2001,
            deezerId = 959183,
            deezerTitle = "Fallin'",
            deezerArtist = "Alicia Keys",
            deezerAlbum = "Songs In A Minor"
        ),
        "00196" to HitsterCard(
            hitsterId = "00196",
            title = "Firework",
            artist = "Katy Perry",
            year = 2010,
            deezerId = 17135111,
            deezerTitle = "Firework",
            deezerArtist = "Katy Perry",
            deezerAlbum = "Teenage Dream: The Complete Confection"
        ),
        "00197" to HitsterCard(
            hitsterId = "00197",
            title = "Genie In A Bottle",
            artist = "Christina Aguilera",
            year = 1999,
            deezerId = 15627386,
            deezerTitle = "Genie In a Bottle",
            deezerArtist = "Christina Aguilera",
            deezerAlbum = "Christina Aguilera (Expanded Edition)"
        ),
        "00198" to HitsterCard(
            hitsterId = "00198",
            title = "Euphoria",
            artist = "Loreen",
            year = 2012,
            deezerId = 67466760,
            deezerTitle = "Euphoria",
            deezerArtist = "Loreen",
            deezerAlbum = "Heal (2013 Edition)"
        ),
        "00199" to HitsterCard(
            hitsterId = "00199",
            title = "Africa",
            artist = "Toto",
            year = 1982,
            deezerId = 1079668,
            deezerTitle = "Africa",
            deezerArtist = "Toto",
            deezerAlbum = "Toto IV"
        ),
        "00200" to HitsterCard(
            hitsterId = "00200",
            title = "At The Hop",
            artist = "Danny & The Juniors",
            year = 1957,
            deezerId = 1903433,
            deezerTitle = "At The Hop",
            deezerArtist = "Danny & The Juniors",
            deezerAlbum = "At The Hop - Greatest Hits"
        ),
        "00201" to HitsterCard(
            hitsterId = "00201",
            title = "Mr. Vain",
            artist = "Culture Beat",
            year = 1993,
            deezerId = 29794781,
            deezerTitle = "Mr. Vain",
            deezerArtist = "Culture Beat",
            deezerAlbum = "Serenity"
        ),
        "00202" to HitsterCard(
            hitsterId = "00202",
            title = "Sweet Dreams (Are Made Of This)",
            artist = "Eurythmics",
            year = 1983,
            deezerId = 15645257,
            deezerTitle = "Sweet Dreams (Are Made of This)",
            deezerArtist = "Eurythmics",
            deezerAlbum = "Greatest Hits"
        ),
        "00203" to HitsterCard(
            hitsterId = "00203",
            title = "All About That Bass",
            artist = "Meghan Trainor",
            year = 2014,
            deezerId = 113420702,
            deezerTitle = "All About That Bass",
            deezerArtist = "Meghan Trainor",
            deezerAlbum = "Title (Expanded Edition)"
        ),
        "00204" to HitsterCard(
            hitsterId = "00204",
            title = "Don't Dream It's Over",
            artist = "Crowded House",
            year = 1986,
            deezerId = 133577580,
            deezerTitle = "Don't Dream It's Over",
            deezerArtist = "Crowded House",
            deezerAlbum = "Crowded House"
        ),
        "00205" to HitsterCard(
            hitsterId = "00205",
            title = "Purple Rain",
            artist = "Prince",
            year = 1984,
            deezerId = 374283061,
            deezerTitle = "Purple Rain (2015 Paisley Park Remaster)",
            deezerArtist = "Prince",
            deezerAlbum = "Purple Rain (Deluxe Expanded Edition)"
        ),
        "00206" to HitsterCard(
            hitsterId = "00206",
            title = "All Of Me",
            artist = "John Legend",
            year = 2013,
            deezerId = 70079770,
            deezerTitle = "All of Me",
            deezerArtist = "John Legend",
            deezerAlbum = "Love In The Future (Expanded Edition)"
        ),
        "00207" to HitsterCard(
            hitsterId = "00207",
            title = "Schöner fremder Mann",
            artist = "Connie Francis",
            year = 1964,
            deezerId = 3055769,
            deezerTitle = "Schöner fremder Mann",
            deezerArtist = "Connie Francis",
            deezerAlbum = "Connie Francis Party Power"
        ),
        "00208" to HitsterCard(
            hitsterId = "00208",
            title = "Love Is All Around",
            artist = "Wet Wet Wet",
            year = 1994,
            deezerId = 1448958662,
            deezerTitle = "Love is All Around",
            deezerArtist = "Wet Wet Wet",
            deezerAlbum = "The Journey"
        ),
        "00209" to HitsterCard(
            hitsterId = "00209",
            title = "Gangsta's Paradise",
            artist = "Coolio (feat. L.V.)",
            year = 1995,
            deezerId = 1584416372,
            deezerTitle = "Gangsta's Paradise",
            deezerArtist = "Coolio",
            deezerAlbum = "Gangsta's Paradise"
        ),
        "00210" to HitsterCard(
            hitsterId = "00210",
            title = "See You Later, Alligator",
            artist = "Bill Haley & His Comets",
            year = 1956,
            deezerId = 908742,
            deezerTitle = "See You Later, Alligator",
            deezerArtist = "Bill Haley & His Comets",
            deezerAlbum = "Rock N' Roll Legends"
        ),
        "00211" to HitsterCard(
            hitsterId = "00211",
            title = "Jump Around",
            artist = "House Of Pain",
            year = 1992,
            deezerId = 1584589772,
            deezerTitle = "Jump Around",
            deezerArtist = "House of Pain",
            deezerAlbum = "Hi-Five: House Of Pain"
        ),
        "00212" to HitsterCard(
            hitsterId = "00212",
            title = "Nothing Compares 2 U",
            artist = "Sinéad O'Connor",
            year = 1990,
            deezerId = 1787569267,
            deezerTitle = "Nothing Compares 2 U",
            deezerArtist = "Sinéad O'Connor",
            deezerAlbum = "So Far: The Best of Sinéad O'Connor"
        ),
        "00213" to HitsterCard(
            hitsterId = "00213",
            title = "Engel",
            artist = "Rammstein",
            year = 1997,
            deezerId = 630595112,
            deezerTitle = "Engel",
            deezerArtist = "Rammstein",
            deezerAlbum = "Sehnsucht"
        ),
        "00214" to HitsterCard(
            hitsterId = "00214",
            title = "Because Of You",
            artist = "Kelly Clarkson",
            year = 2005,
            deezerId = 546130,
            deezerTitle = "Because of You",
            deezerArtist = "Kelly Clarkson",
            deezerAlbum = "Breakaway"
        ),
        "00215" to HitsterCard(
            hitsterId = "00215",
            title = "Du trägst keine Liebe in dir",
            artist = "Echt",
            year = 1999,
            deezerId = 2305250,
            deezerTitle = "Du trägst keine Liebe in dir",
            deezerArtist = "Echt",
            deezerAlbum = "Freischwimmer"
        ),
        "00216" to HitsterCard(
            hitsterId = "00216",
            title = "Part-Time Lovers",
            artist = "Stevie Wonder",
            year = 1985,
            deezerId = 24967291,
            deezerTitle = "Part-Time Lover",
            deezerArtist = "Stevie Wonder",
            deezerAlbum = "In Square Circle"
        ),
        "00217" to HitsterCard(
            hitsterId = "00217",
            title = "Walk Of Life",
            artist = "Dire Straits",
            year = 1985,
            deezerId = 2514683,
            deezerTitle = "Walk Of Life",
            deezerArtist = "Dire Straits",
            deezerAlbum = "The Best Of Dire Straits & Mark Knopfler - Private Investigations"
        ),
        "00218" to HitsterCard(
            hitsterId = "00218",
            title = "All That She Wants",
            artist = "Ace Of Base",
            year = 1992,
            deezerId = 94676692,
            deezerTitle = "All That She Wants",
            deezerArtist = "Ace of Base",
            deezerAlbum = "Happy Nation (U.S. Version) (Remastered)"
        ),
        "00219" to HitsterCard(
            hitsterId = "00219",
            title = "Alles nur geklaut",
            artist = "Die Prinzen",
            year = 1993,
            deezerId = 2200874917,
            deezerTitle = "Alles nur geklaut",
            deezerArtist = "Die Prinzen",
            deezerAlbum = "Alles nur geklaut"
        ),
        "00220" to HitsterCard(
            hitsterId = "00220",
            title = "Waterloo",
            artist = "ABBA",
            year = 1974,
            deezerId = 76376889,
            deezerTitle = "Waterloo",
            deezerArtist = "ABBA",
            deezerAlbum = "Abba Gold Anniversary Edition"
        ),
        "00221" to HitsterCard(
            hitsterId = "00221",
            title = "Heaven",
            artist = "Bryan Adams",
            year = 1985,
            deezerId = 88902737,
            deezerTitle = "Heaven",
            deezerArtist = "Bryan Adams",
            deezerAlbum = "Reckless"
        ),
        "00222" to HitsterCard(
            hitsterId = "00222",
            title = "Durch den Monsun",
            artist = "Tokio Hotel",
            year = 2005,
            deezerId = 7718640,
            deezerTitle = "Durch den Monsun (Radio Mix)",
            deezerArtist = "Tokio Hotel",
            deezerAlbum = "Best Of (German Version)"
        ),
        "00223" to HitsterCard(
            hitsterId = "00223",
            title = "The Loco-Motion",
            artist = "Little Eva",
            year = 1962,
            deezerId = 100772638,
            deezerTitle = "The Loco-Motion",
            deezerArtist = "Little Eva",
            deezerAlbum = "Moochin' Abouts Stateside Hitlist 1962"
        ),
        "00224" to HitsterCard(
            hitsterId = "00224",
            title = "Smells Like Teen Spirit",
            artist = "Nirvana",
            year = 1991,
            deezerId = 13791930,
            deezerTitle = "Smells Like Teen Spirit",
            deezerArtist = "Nirvana",
            deezerAlbum = "Nevermind (Remastered)"
        ),
        "00225" to HitsterCard(
            hitsterId = "00225",
            title = "Fly Away",
            artist = "Lenny Kravitz",
            year = 1998,
            deezerId = 3156968,
            deezerTitle = "Fly Away",
            deezerArtist = "Lenny Kravitz",
            deezerAlbum = "5"
        ),
        "00226" to HitsterCard(
            hitsterId = "00226",
            title = "Smalltown Boy",
            artist = "Bronski Beat",
            year = 1984,
            deezerId = 428735732,
            deezerTitle = "Smalltown Boy",
            deezerArtist = "Bronski Beat",
            deezerAlbum = "The Very Best Of Jimmy Somerville, Bronski Beat & The Communards"
        ),
        "00227" to HitsterCard(
            hitsterId = "00227",
            title = "Ring Of Fire",
            artist = "Johnny Cash",
            year = 1963,
            deezerId = 856630,
            deezerTitle = "Ring of Fire",
            deezerArtist = "Johnny Cash",
            deezerAlbum = "The Essential Johnny Cash"
        ),
        "00228" to HitsterCard(
            hitsterId = "00228",
            title = "These Boots Are Made For Walkin'",
            artist = "Nancy Sinatra",
            year = 1965,
            deezerId = 71323824,
            deezerTitle = "These Boots Are Made for Walkin'",
            deezerArtist = "Nancy Sinatra",
            deezerAlbum = "Kid Stuff"
        ),
        "00229" to HitsterCard(
            hitsterId = "00229",
            title = "Price Tag",
            artist = "Jessie J (feat. B.O.B)",
            year = 2011,
            deezerId = 14405185,
            deezerTitle = "Price Tag",
            deezerArtist = "Jessie J",
            deezerAlbum = "Who You Are (Platinum Edition)"
        ),
        "00230" to HitsterCard(
            hitsterId = "00230",
            title = "Itsy Bitsy Teenie Weenie Honolulu Strand Bikini",
            artist = "Caterina Valente & Silvio Francesco",
            year = 1960,
            deezerId = 14189399,
            deezerTitle = "Itsy Bitsy Teenie Weenie Honolulu Strand Bikini",
            deezerArtist = "Caterina Valente",
            deezerAlbum = "Itsy Bitsy Teenie Weenie Honolulu Strand Bikini"
        ),
        "00231" to HitsterCard(
            hitsterId = "00231",
            title = "Informer",
            artist = "Snow",
            year = 1992,
            deezerId = 3884974,
            deezerTitle = "Informer",
            deezerArtist = "SNoW",
            deezerAlbum = "12 Inches Of Snow"
        ),
        "00232" to HitsterCard(
            hitsterId = "00232",
            title = "Das Beste",
            artist = "Silbermond",
            year = 2006,
            deezerId = 15478334,
            deezerTitle = "Das Beste",
            deezerArtist = "Silbermond",
            deezerAlbum = "Laut Gedacht (Re-Edition)"
        ),
        "00233" to HitsterCard(
            hitsterId = "00233",
            title = "Celebration",
            artist = "Kool & The Gang",
            year = 1980,
            deezerId = 95873824,
            deezerTitle = "Celebration",
            deezerArtist = "Kool & The Gang",
            deezerAlbum = "Celebrate!"
        ),
        "00234" to HitsterCard(
            hitsterId = "00234",
            title = "Believe",
            artist = "Cher",
            year = 1998,
            deezerId = 786717,
            deezerTitle = "Believe",
            deezerArtist = "Cher",
            deezerAlbum = "Believe"
        ),
        "00235" to HitsterCard(
            hitsterId = "00235",
            title = "Knock On Wood",
            artist = "Amii Stewart",
            year = 1979,
            deezerId = 10370275,
            deezerTitle = "Knock On Wood",
            deezerArtist = "Amii Stewart",
            deezerAlbum = "Knock On Wood"
        ),
        "00236" to HitsterCard(
            hitsterId = "00236",
            title = "Mercy",
            artist = "Duffy",
            year = 2008,
            deezerId = 2606173,
            deezerTitle = "Mercy",
            deezerArtist = "Duffy",
            deezerAlbum = "Rockferry"
        ),
        "00237" to HitsterCard(
            hitsterId = "00237",
            title = "We Are Family",
            artist = "Sister Sledge",
            year = 1979,
            deezerId = 1872880,
            deezerTitle = "We Are Family",
            deezerArtist = "Sister Sledge",
            deezerAlbum = "Soul Weekender"
        ),
        "00238" to HitsterCard(
            hitsterId = "00238",
            title = "In The Summertime",
            artist = "Mungo Jerry",
            year = 1970,
            deezerId = 131302482,
            deezerTitle = "In the Summertime",
            deezerArtist = "Mungo Jerry",
            deezerAlbum = "In the Summertime"
        ),
        "00239" to HitsterCard(
            hitsterId = "00239",
            title = "Return Of The Mack",
            artist = "Mark Morrison",
            year = 1996,
            deezerId = 703748,
            deezerTitle = "Return of the Mack",
            deezerArtist = "Mark Morrison",
            deezerAlbum = "Return of the Mack"
        ),
        "00240" to HitsterCard(
            hitsterId = "00240",
            title = "Zuckerpuppe (aus der Bauchtanz-Truppe)",
            artist = "Bill Ramsey",
            year = 1961,
            deezerId = 92788994,
            deezerTitle = "Zuckerpuppe (Aus der Bauchtanz-Truppe)",
            deezerArtist = "Bill Ramsey",
            deezerAlbum = "Pigalle, Pigalle"
        ),
        "00241" to HitsterCard(
            hitsterId = "00241",
            title = "Nah Neh Nah",
            artist = "Vaya Con Dios",
            year = 1990,
            deezerId = 1053435,
            deezerTitle = "Nah Neh Nah",
            deezerArtist = "Vaya Con Dios",
            deezerAlbum = "The Best Of Vaya Con Dios"
        ),
        "00242" to HitsterCard(
            hitsterId = "00242",
            title = "Emanuela",
            artist = "Fettes Brot",
            year = 2005,
            deezerId = 3558285081,
            deezerTitle = "Emanuela",
            deezerArtist = "Fettes Brot",
            deezerAlbum = "Am Wasser gebaut (Trockendock Edition)"
        ),
        "00243" to HitsterCard(
            hitsterId = "00243",
            title = "Skandal im Sperrbezirk",
            artist = "Spider Murphy Gang",
            year = 1981,
            deezerId = 3123329,
            deezerTitle = "Skandal im Sperrbezirk",
            deezerArtist = "Spider Murphy Gang",
            deezerAlbum = "Greatest Hits"
        ),
        "00244" to HitsterCard(
            hitsterId = "00244",
            title = "It's My Life",
            artist = "Bon Jovi",
            year = 2000,
            deezerId = 577487252,
            deezerTitle = "It's My Life",
            deezerArtist = "Bon Jovi",
            deezerAlbum = "Bon Jovi Greatest Hits - The Ultimate Collection (Deluxe)"
        ),
        "00245" to HitsterCard(
            hitsterId = "00245",
            title = "Ganz Paris träumt von der Liebe",
            artist = "Caterina Valente",
            year = 1954,
            deezerId = 7524269,
            deezerTitle = "Ganz Paris träumt von der Liebe",
            deezerArtist = "Caterina Valente",
            deezerAlbum = "Bonjour Catherine"
        ),
        "00246" to HitsterCard(
            hitsterId = "00246",
            title = "Crazy In Love",
            artist = "Beyoncé (feat. Jay-Z)",
            year = 2003,
            deezerId = 13138698,
            deezerTitle = "Crazy In Love (feat. JAY-Z)",
            deezerArtist = "Beyoncé",
            deezerAlbum = "Dangerously In Love"
        ),
        "00247" to HitsterCard(
            hitsterId = "00247",
            title = "Blinding Lights",
            artist = "The Weeknd",
            year = 2019,
            deezerId = 908604612,
            deezerTitle = "Blinding Lights",
            deezerArtist = "The Weeknd",
            deezerAlbum = "After Hours"
        ),
        "00248" to HitsterCard(
            hitsterId = "00248",
            title = "Die Gitarre und das Meer",
            artist = "Freddy Quinn",
            year = 1959,
            deezerId = 16476740,
            deezerTitle = "Die Gitarre und das Meer",
            deezerArtist = "Freddy Quinn",
            deezerAlbum = "Freddy Quinn - Die grossen Hits"
        ),
        "00249" to HitsterCard(
            hitsterId = "00249",
            title = "Save Tonight",
            artist = "Eagle-Eye Cherry",
            year = 1997,
            deezerId = 2252935,
            deezerTitle = "Save Tonight",
            deezerArtist = "Eagle-Eye Cherry",
            deezerAlbum = "Desireless"
        ),
        "00250" to HitsterCard(
            hitsterId = "00250",
            title = "Holding Back The Years",
            artist = "Simply Red",
            year = 1985,
            deezerId = 2477001,
            deezerTitle = "Holding Back the Years (2008 Remaster)",
            deezerArtist = "Simply Red",
            deezerAlbum = "Picture Book (Expanded Version)"
        ),
        "00251" to HitsterCard(
            hitsterId = "00251",
            title = "Die Eine",
            artist = "Die Firma",
            year = 1998,
            deezerId = 974865722,
            deezerTitle = "Die Eine",
            deezerArtist = "Die Firma",
            deezerAlbum = "Spiel des Lebens / Spiel des Todes (Deluxe Edition)"
        ),
        "00252" to HitsterCard(
            hitsterId = "00252",
            title = "1000 und 1 Nacht (Zoom!)",
            artist = "Klaus Lage",
            year = 1984,
            deezerId = 13460739,
            deezerTitle = "1000 und 1 Nacht (Zoom!)",
            deezerArtist = "Klaus Lage",
            deezerAlbum = "Ballermann Hits Silvester"
        ),
        "00253" to HitsterCard(
            hitsterId = "00253",
            title = "Die guten Zeiten",
            artist = "Wincent Weiss & Johannes Oerding",
            year = 2021,
            deezerId = 1394046942,
            deezerTitle = "Die guten Zeiten",
            deezerArtist = "Wincent Weiss",
            deezerAlbum = "Die guten Zeiten"
        ),
        "00254" to HitsterCard(
            hitsterId = "00254",
            title = "Circles",
            artist = "Post Malone",
            year = 2019,
            deezerId = 747399352,
            deezerTitle = "Circles",
            deezerArtist = "Post Malone",
            deezerAlbum = "Hollywood's Bleeding"
        ),
        "00255" to HitsterCard(
            hitsterId = "00255",
            title = "Marmor, Stein und Eisen bricht",
            artist = "Drafi Deutscher",
            year = 1965,
            deezerId = 697623,
            deezerTitle = "Marmor, Stein und Eisen bricht",
            deezerArtist = "Drafi Deutscher",
            deezerAlbum = "Drafi!"
        ),
        "00256" to HitsterCard(
            hitsterId = "00256",
            title = "Why",
            artist = "Annie Lennox",
            year = 1992,
            deezerId = 2889773,
            deezerTitle = "Why",
            deezerArtist = "Annie Lennox",
            deezerAlbum = "The Annie Lennox Collection"
        ),
        "00257" to HitsterCard(
            hitsterId = "00257",
            title = "Cheap Thrills",
            artist = "Sia",
            year = 2015,
            deezerId = 118195184,
            deezerTitle = "Cheap Thrills",
            deezerArtist = "Sia",
            deezerAlbum = "This Is Acting"
        ),
        "00258" to HitsterCard(
            hitsterId = "00258",
            title = "Under Pressure",
            artist = "Queen (feat. David Bowie)",
            year = 1981,
            deezerId = 12274786,
            deezerTitle = "Under Pressure (Remastered 2011)",
            deezerArtist = "Queen",
            deezerAlbum = "Hot Space (Deluxe Edition 2011 Remaster)"
        ),
        "00259" to HitsterCard(
            hitsterId = "00259",
            title = "Numb/Encore",
            artist = "Jay-Z & Linkin Park",
            year = 2004,
            deezerId = 676954,
            deezerTitle = "Numb / Encore",
            deezerArtist = "JAY Z",
            deezerAlbum = "Collision Course"
        ),
        "00260" to HitsterCard(
            hitsterId = "00260",
            title = "Good Vibrations",
            artist = "The Beach Boys",
            year = 1966,
            deezerId = 14176175,
            deezerTitle = "Good Vibrations",
            deezerArtist = "The Beach Boys",
            deezerAlbum = "The Smile Sessions"
        ),
        "00261" to HitsterCard(
            hitsterId = "00261",
            title = "Ain't That A Shame",
            artist = "Fats Domino",
            year = 1955,
            deezerId = 3092104,
            deezerTitle = "Ain't That A Shame",
            deezerArtist = "Fats Domino",
            deezerAlbum = "Essential"
        ),
        "00262" to HitsterCard(
            hitsterId = "00262",
            title = "Das Boot",
            artist = "U96",
            year = 1992,
            deezerId = 94877938,
            deezerTitle = "Das Boot",
            deezerArtist = "U96",
            deezerAlbum = "Das Boot"
        ),
        "00263" to HitsterCard(
            hitsterId = "00263",
            title = "Praise You",
            artist = "Fatboy Slim",
            year = 1999,
            deezerId = 131198242,
            deezerTitle = "Praise You",
            deezerArtist = "Fatboy Slim",
            deezerAlbum = "You've Come a Long Way Baby"
        ),
        "00264" to HitsterCard(
            hitsterId = "00264",
            title = "Could I Have This Kiss Forever",
            artist = "Enrique Iglesias & Whitney Houston",
            year = 2000,
            deezerId = 765849122,
            deezerTitle = "Could I Have This Kiss Forever",
            deezerArtist = "Enrique Iglesias",
            deezerAlbum = "Greatest Hits"
        ),
        "00265" to HitsterCard(
            hitsterId = "00265",
            title = "Major Tom (völlig losgelöst)",
            artist = "Peter Schilling",
            year = 1982,
            deezerId = 1222024892,
            deezerTitle = "Major Tom (Völlig losgelöst) (Single Version)",
            deezerArtist = "Peter Schilling",
            deezerAlbum = "Major Tom (Völlig losgelöst) (All Versions)"
        ),
        "00266" to HitsterCard(
            hitsterId = "00266",
            title = "Where Is The Love?",
            artist = "Black Eyed Peas",
            year = 2003,
            deezerId = 1697351967,
            deezerTitle = "Where Is The Love?",
            deezerArtist = "Black Eyed Peas",
            deezerAlbum = "Elephunk"
        ),
        "00267" to HitsterCard(
            hitsterId = "00267",
            title = "It Wasn't Me",
            artist = "Shaggy",
            year = 2000,
            deezerId = 2122532,
            deezerTitle = "It Wasn't Me",
            deezerArtist = "Shaggy",
            deezerAlbum = "The Boombastic Collection - Best Of Shaggy (International Version)"
        ),
        "00268" to HitsterCard(
            hitsterId = "00268",
            title = "Dance Monkey",
            artist = "Tones And I",
            year = 2019,
            deezerId = 739870792,
            deezerTitle = "Dance Monkey",
            deezerArtist = "Tones and I",
            deezerAlbum = "The Kids Are Coming"
        ),
        "00269" to HitsterCard(
            hitsterId = "00269",
            title = "It's In His Kiss (The Shoop Shoop Song)",
            artist = "Betty Everett",
            year = 1964,
            deezerId = 78309531,
            deezerTitle = "It's in His Kiss (The Shoop Shoop Song)",
            deezerArtist = "Betty Everett",
            deezerAlbum = "Chicago Hit Factory - The Vee-Jay Story 1953-1966"
        ),
        "00270" to HitsterCard(
            hitsterId = "00270",
            title = "I Got You (I Feel Good)",
            artist = "James Brown & The Famous Flames",
            year = 1965,
            deezerId = 820316542,
            deezerTitle = "I Feel That Old Feeling Coming On",
            deezerArtist = "James Brown",
            deezerAlbum = "You Got the Power"
        ),
        "00271" to HitsterCard(
            hitsterId = "00271",
            title = "Lonely",
            artist = "Akon",
            year = 2005,
            deezerId = 78869181,
            deezerTitle = "Lonely",
            deezerArtist = "Akon",
            deezerAlbum = "Trouble"
        ),
        "00272" to HitsterCard(
            hitsterId = "00272",
            title = "Super Freak",
            artist = "Rick James",
            year = 1981,
            deezerId = 543150512,
            deezerTitle = "Super Freak",
            deezerArtist = "Rick James",
            deezerAlbum = "Street Songs (Expanded Edition)"
        ),
        "00273" to HitsterCard(
            hitsterId = "00273",
            title = "Tränen lügen nicht",
            artist = "Michael Holm",
            year = 1974,
            deezerId = 16267932,
            deezerTitle = "Tränen lügen nicht",
            deezerArtist = "Michael Holm",
            deezerAlbum = "Seine grossen Erfolge"
        ),
        "00274" to HitsterCard(
            hitsterId = "00274",
            title = "When A Man Loves A Woman",
            artist = "Percy Sledge",
            year = 1966,
            deezerId = 4209649,
            deezerTitle = "When a Man Loves a Woman",
            deezerArtist = "Percy Sledge",
            deezerAlbum = "When a Man Loves a Woman / Love Me Like You Mean It"
        ),
        "00275" to HitsterCard(
            hitsterId = "00275",
            title = "Blue Bayou",
            artist = "Linda Ronstadt",
            year = 1977,
            deezerId = 3919443,
            deezerTitle = "Blue Bayou",
            deezerArtist = "Linda Ronstadt",
            deezerAlbum = "Simple Dreams"
        ),
        "00276" to HitsterCard(
            hitsterId = "00276",
            title = "Jolene",
            artist = "Dolly Parton",
            year = 1973,
            deezerId = 114422238,
            deezerTitle = "Jolene",
            deezerArtist = "Dolly Parton",
            deezerAlbum = "Jolene"
        ),
        "00277" to HitsterCard(
            hitsterId = "00277",
            title = "Like A Prayer",
            artist = "Mad'House",
            year = 2002,
            deezerId = 4631883,
            deezerTitle = "Like a Prayer",
            deezerArtist = "Mad'House",
            deezerAlbum = "Absolutely Mad"
        ),
        "00278" to HitsterCard(
            hitsterId = "00278",
            title = "That's Amore",
            artist = "Dean Martin",
            year = 1953,
            deezerId = 920521062,
            deezerTitle = "That's Amore",
            deezerArtist = "Dean Martin",
            deezerAlbum = "The Legendary Crooners - Dean Martin"
        ),
        "00279" to HitsterCard(
            hitsterId = "00279",
            title = "Piano Man",
            artist = "Billy Joel",
            year = 1973,
            deezerId = 14333215,
            deezerTitle = "Piano Man",
            deezerArtist = "Billy Joel",
            deezerAlbum = "Piano Man (Legacy Edition)"
        ),
        "00280" to HitsterCard(
            hitsterId = "00280",
            title = "Er hat ein knallrotes Gummiboot",
            artist = "Wencke Myhre",
            year = 1970,
            deezerId = 7905433,
            deezerTitle = "Er hat ein knallrotes Gummiboot",
            deezerArtist = "Wencke Myhre",
            deezerAlbum = "Glanzlichter"
        ),
        "00281" to HitsterCard(
            hitsterId = "00281",
            title = "Space Oddity",
            artist = "David Bowie",
            year = 1969,
            deezerId = 107465566,
            deezerTitle = "Space Oddity (2015 Remaster)",
            deezerArtist = "David Bowie",
            deezerAlbum = "David Bowie (aka Space Oddity) (2015 Remaster)"
        ),
        "00282" to HitsterCard(
            hitsterId = "00282",
            title = "Anyone",
            artist = "Justin Bieber",
            year = 2021,
            deezerId = 1194148902,
            deezerTitle = "Anyone",
            deezerArtist = "Justin Bieber",
            deezerAlbum = "Anyone"
        ),
        "00283" to HitsterCard(
            hitsterId = "00283",
            title = "Scars To Your Beautiful",
            artist = "Alessia Cara",
            year = 2016,
            deezerId = 120739016,
            deezerTitle = "Scars To Your Beautiful",
            deezerArtist = "Alessia Cara",
            deezerAlbum = "Know-It-All (Deluxe)"
        ),
        "00284" to HitsterCard(
            hitsterId = "00284",
            title = "Get Back",
            artist = "The Beatles",
            year = 1969,
            deezerId = 126848569,
            deezerTitle = "Get Back",
            deezerArtist = "The Beatles",
            deezerAlbum = "Love"
        ),
        "00285" to HitsterCard(
            hitsterId = "00285",
            title = "Waka Waka (This Time For Africa)",
            artist = "Shakira",
            year = 2010,
            deezerId = 68473089,
            deezerTitle = "Waka Waka (This Time for Africa) (feat. Freshlyground)",
            deezerArtist = "Shakira",
            deezerAlbum = "Party Hits: Summer Edition"
        ),
        "00286" to HitsterCard(
            hitsterId = "00286",
            title = "Let's Stick Together",
            artist = "Bryan Ferry",
            year = 1976,
            deezerId = 3135015,
            deezerTitle = "Let's Stick Together",
            deezerArtist = "Bryan Ferry",
            deezerAlbum = "Let's Stick Together"
        ),
        "00287" to HitsterCard(
            hitsterId = "00287",
            title = "Put Your Records On",
            artist = "Corinne Bailey Rae",
            year = 2006,
            deezerId = 3119484,
            deezerTitle = "Put Your Records On",
            deezerArtist = "Corinne Bailey Rae",
            deezerAlbum = "Corinne Bailey Rae"
        ),
        "00288" to HitsterCard(
            hitsterId = "00288",
            title = "Ice Ice Baby",
            artist = "Vanilla Ice",
            year = 1990,
            deezerId = 2397026,
            deezerTitle = "Ice Ice Baby",
            deezerArtist = "Vanilla Ice",
            deezerAlbum = "Vanilla Ice Is Back! - Hip Hop Classics"
        ),
        "00289" to HitsterCard(
            hitsterId = "00289",
            title = "When I Need You",
            artist = "Leo Sayer",
            year = 1977,
            deezerId = 12179238,
            deezerTitle = "When I Need You",
            deezerArtist = "Leo Sayer",
            deezerAlbum = "The Show Must Go On: The Very Best Of Leo Sayer"
        ),
        "00290" to HitsterCard(
            hitsterId = "00290",
            title = "Bad Guy",
            artist = "Billie Eilish",
            year = 2019,
            deezerId = 655095912,
            deezerTitle = "bad guy",
            deezerArtist = "Billie Eilish",
            deezerAlbum = "WHEN WE ALL FALL ASLEEP, WHERE DO WE GO?"
        ),
        "00291" to HitsterCard(
            hitsterId = "00291",
            title = "Feel It Still",
            artist = "Portugal. The Man",
            year = 2017,
            deezerId = 371593461,
            deezerTitle = "Feel It Still",
            deezerArtist = "Portugal. The Man",
            deezerAlbum = "Woodstock"
        ),
        "00292" to HitsterCard(
            hitsterId = "00292",
            title = "Shallow",
            artist = "Lady Gaga & Bradley Cooper",
            year = 2018,
            deezerId = 561856742,
            deezerTitle = "Shallow",
            deezerArtist = "Lady Gaga",
            deezerAlbum = "A Star Is Born Soundtrack"
        ),
        "00293" to HitsterCard(
            hitsterId = "00293",
            title = "Vom selben Stern",
            artist = "Ich + Ich",
            year = 2007,
            deezerId = 1278137272,
            deezerTitle = "Vom selben Stern (Radio Edit)",
            deezerArtist = "Ich + Ich",
            deezerAlbum = "Vom selben Stern (Exklusive Version)"
        ),
        "00294" to HitsterCard(
            hitsterId = "00294",
            title = "Hey, Soul Sister",
            artist = "Train",
            year = 2009,
            deezerId = 563683852,
            deezerTitle = "Hey, Soul Sister",
            deezerArtist = "Train",
            deezerAlbum = "Save Me, San Francisco (Golden Gate Edition)"
        ),
        "00295" to HitsterCard(
            hitsterId = "00295",
            title = "Can't Help Falling In Love",
            artist = "Elvis Presley",
            year = 1961,
            deezerId = 551383,
            deezerTitle = "Can't Help Falling In Love",
            deezerArtist = "Elvis Presley",
            deezerAlbum = "Elvis At The Movies"
        ),
        "00296" to HitsterCard(
            hitsterId = "00296",
            title = "Call On Me",
            artist = "Eric Prydz",
            year = 2004,
            deezerId = 340051261,
            deezerTitle = "Call on Me",
            deezerArtist = "Eric Prydz",
            deezerAlbum = "Call On Me (Remixes)"
        ),
        "00297" to HitsterCard(
            hitsterId = "00297",
            title = "I Heard It Through The Grapevine",
            artist = "Marvin Gaye",
            year = 1968,
            deezerId = 132196486,
            deezerTitle = "I Heard It Through The Grapevine",
            deezerArtist = "Marvin Gaye",
            deezerAlbum = "Bridget Jones’s Baby (Original Motion Picture Soundtrack)"
        ),
        "00298" to HitsterCard(
            hitsterId = "00298",
            title = "Ein Bett im Kornfeld",
            artist = "Jürgen Drews",
            year = 1976,
            deezerId = 62238193,
            deezerTitle = "Ein Bett im Kornfeld",
            deezerArtist = "Jürgen Drews",
            deezerAlbum = "Musik Box - Teil 1"
        ),
        "00299" to HitsterCard(
            hitsterId = "00299",
            title = "Dancing In The Moonlight",
            artist = "Toploader",
            year = 2000,
            deezerId = 10434104,
            deezerTitle = "Dancing in the Moonlight",
            deezerArtist = "Toploader",
            deezerAlbum = "Dancing In The Moonlight: The Best Of Toploader"
        ),
        "00300" to HitsterCard(
            hitsterId = "00300",
            title = "Escape (The Piña Colada Song)",
            artist = "Rupert Holmes",
            year = 1979,
            deezerId = 2801132,
            deezerTitle = "Escape (The Pina Colada Song) (Single Version)",
            deezerArtist = "Rupert Holmes",
            deezerAlbum = "Escape...The Best Of"
        ),
        "00301" to HitsterCard(
            hitsterId = "00301",
            title = "Thunder",
            artist = "Imagine Dragons",
            year = 2017,
            deezerId = 528330501,
            deezerTitle = "Thunder",
            deezerArtist = "Imagine Dragons",
            deezerAlbum = "Evolve"
        ),
        "00302" to HitsterCard(
            hitsterId = "00302",
            title = "Oh, Pretty Woman",
            artist = "Roy Orbison",
            year = 1964,
            deezerId = 78033230,
            deezerTitle = "Oh, Pretty Woman",
            deezerArtist = "Roy Orbison",
            deezerAlbum = "Oh, Pretty Woman"
        ),
        "00303" to HitsterCard(
            hitsterId = "00303",
            title = "Lollipop",
            artist = "The Chordettes",
            year = 1958,
            deezerId = 3991861,
            deezerTitle = "Lollipop",
            deezerArtist = "The Chordettes",
            deezerAlbum = "Mister  Sand Man"
        ),
        "00304" to HitsterCard(
            hitsterId = "00304",
            title = "No Woman No Cry",
            artist = "Bob Marley & The Wailers",
            year = 1974,
            deezerId = 1583148,
            deezerTitle = "No Woman No Cry",
            deezerArtist = "Bob Marley & The Wailers",
            deezerAlbum = "Natty Dread"
        ),
        "00305" to HitsterCard(
            hitsterId = "00305",
            title = "Respect",
            artist = "Aretha Franklin",
            year = 1967,
            deezerId = 904732,
            deezerTitle = "Respect",
            deezerArtist = "Aretha Franklin",
            deezerAlbum = "I Never Loved a Man the Way I Love You"
        ),
        "00306" to HitsterCard(
            hitsterId = "00306",
            title = "You Can Get It If You Really Want",
            artist = "Jimmy Cliff",
            year = 1970,
            deezerId = 1106498962,
            deezerTitle = "You Can Get It If You Really Want",
            deezerArtist = "Jimmy Cliff",
            deezerAlbum = "The Harder They Come (Original Motion Picture Soundtrack)"
        ),
        "00307" to HitsterCard(
            hitsterId = "00307",
            title = "Wooly Bully",
            artist = "Sam The Sham & The Pharaohs",
            year = 1965,
            deezerId = 1844097487,
            deezerTitle = "Wooly Bully",
            deezerArtist = "Sam the Sham & The Pharaohs",
            deezerAlbum = "The MGM Singles"
        ),
        "00308" to HitsterCard(
            hitsterId = "00308",
            title = "Fireflies",
            artist = "Owl City",
            year = 2009,
            deezerId = 4188437,
            deezerTitle = "Fireflies",
            deezerArtist = "Owl City",
            deezerAlbum = "Ocean Eyes"
        )
    )

    override suspend fun getCardById(cardId: String): HitsterCard? {
        // Simulate network delay
        delay(simulatedDelay)

        // Normalize card ID (handle both "1" and "00001" formats)
        val normalizedId = cardId.padStart(5, '0')

        return cardDatabase[normalizedId]
    }
}
