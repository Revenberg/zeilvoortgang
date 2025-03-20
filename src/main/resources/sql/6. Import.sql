-- Insert data into progressStatussen table
INSERT INTO `zeilvoortgang`.`progressStatussen` (`progressStatusId`, `description`, `longDescription`) VALUES
(0, ' ', ''),
(2, 'U', 'Uitleg'),
(4, 'S', 'Starter'),
(6, 'H', 'Half gevorderd'),
(8, '7', 'Gevorderde'),
(10, 'B', 'Behaald');

-- Insert data into levels table
INSERT IGNORE INTO `zeilvoortgang`.`levels` (`levelId`, `description`, `image`) VALUES 
(1, 'CWO 1', 'kielboot_i.png'),
(2, 'CWO 2', 'kielboot_ii.png'),
(3, 'CWO 3', 'kielboot_iii.png'),
(4, 'CWO 4', 'kielboot_vi.png');


INSERT IGNORE INTO `zeilvoortgang`.`methodieken` (`methodiekId`, `levelId`, `soort`, `lastUpdateTMS`, `lastUpdateIdentifier`, `methodiek`, `description` ) VALUES
(1, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT', 'Het schip zeilklaar en nachtklaar maken', 'Zeilklaar maken: Zeilkle(e)d(en) verwijderen, kraanlijn doorzetten en mik of schaar verwijderen, fok aanslaan, fokkeschoten inscheren, vallen aanslaan, Inventaris controleren.\r\nKlaarmaken voor de nacht: Vallen losmaken en in het want of langs de mast (rammelvrij) wegwerken, Fok in zeilzak, grootzeil opdoeken, giek (en gaffel) op de mik (schaar) leggen, Kraanlijn loszetten, Zeilkle(e)d(en) aanbrengen, inventaris opruimen.'),
(2, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT', 'Verhalen van het schip', 'Zonder gebruik te maken van de motor, Alle manieren met spierkracht zijn toegelaten met dien verstande dat het verhalen geen gevaar op mag leveren voor bemanning, materiaal of andere scheepvaart, Op het schip zelf dient zo veel mogelijk vanuit de kuip gewerkt te worden.'),
(3, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Stilliggend hijsen en strijken van de zeilen',
'Met de kop (nagenoeg) in de wind gaan liggen, Zonodig verhalen, Iemand/iets ervoor zorg laten dragen dat het schip niet tegen de wal komt, Bemanning voorin of aan de kant van de kraanlijn plaats laten nemen.
Grootzeil hijsen: Grootschoot los, Zeilbandjes los, Zonodig zeil opvangen, Gaffel tot ongeveer 45 graden (*), Vallen samen (*), Piekeval tijdelijk vastzetten (*), Klauwval vastzetten (*), Halstalie vast, Rijglijn/rakbanden zonodig corrigeren, Piek stellen, zodat een plooi van nok naar hals resteert (*), Kraanlijn zodanig los dat het zeil er geen hinder van ondervindt.
Fok hijsen: Val losmaken, Zonodig naar de kuip gaan, Schoothoek aan schoot lostrekken (val ontspannen en beheerst trekken), Strietsen (dwars op de val trekken; de ruimte die ontstaat over de korvijnagel of kikker met de andere hand wegnemen), Val beleggen, Vallen/kraanlijn opschieten.
(*) alleen voor gaffelzeilen'),

(4, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Stand en bediening van de zeilen', 
'Zowel bij het varen van een rechte koers als bij het maken van bochten dient steeds zoveel mogelijk de juiste zeilstand te worden gevoerd, De zeilen dienen zoveel mogelijk gevierd te zijn zonder dat het voorlijk daarbij kilt, Bij oploeven is het killen van de fok en bij afvallen is het killen van het grootzeil in bescheiden mate noodzakelijk, De zeilen moeten het sturen van de boot ondersteunen.'),

(5, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Sturen, roer- en schootbediening', 
'Het schip met behulp van het roer en de zeilen een rechte koers en bochten kunnen laten varen, zodanig dat een aangewezen punt zonder onnodige omwegen wordt aangezeild.'),

(6, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Overstag gaan', 
'Van hoog aan de wind over de ene boeg naar hoog aan de wind over de andere boeg, Als er niet hoog aan de wind wordt gevaren, dan kan een opdraaiende beweging worden gemaakt waarbij vloeiend wordt overgegaan in de overstagmanoeuvre.
Commando’s:
- “Klaar om te wenden”: waarschuwingscommando, Indien nodig ook te gebruiken in sloten en kanalen, Bemanning maakt zich gereed.
- “Ree”: start van de manoeuvre, Fokkeschoot 10 tot 15 cm vieren (= fok killend bij), Grootschoot zonodig en zo mogelijk enige decimeters aantrekken.
- “Fok bak”: alleen als het nodig is, Als de boot nagenoeg in de wind ligt, de fokkeschoot aan de oude loefzijde weer aantrekken.
- “Fok over”: als de boot net door wind heen is, ‘Oude’ fokkeschoot opvieren en de ‘nieuwe’ fokkeschoot aantrekken totdat de schoothoek net niet meer klappert.
- “Fok aan”: als de boot weer wat snelheid heeft gekregen, De bemanning zet de fok strak, Dit moet zonder ‘rukken’ gebeuren, In de draai moet de fokkeschoot zoveel aangetrokken worden dat de fok geen wind vangt maar dat het klapperen belemmerd wordt, Zo min mogelijk roer geven (alleen bij heel weinig wind of veel golfslag is meer roer geven noodzakelijk), Stuurman met het gezicht naar voren gaan verzitten.'),

(7, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Opkruisen in breed vaarwater', 
'Goed hoog aan de wind varend en zonodig overstag gaand een in de wind gelegen punt aanzeilen.'),

(8, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Gijpen', 
'- Aan zien komen wanneer er gegijpt moet worden.
- De stuurman attendeert de bemanning op de komende gijp.
- Het overkomen van het zeil moet pal voor de wind gebeuren.
- Na de gijp zit de stuurman aan de hoge zijde.
- Het schip moet een vloeiende koers blijven varen.
- ‘Nieuwe’ fokkeschoot wordt gepakt, Eventueel opnieuw fok te loevert zetten.
- Direct voor en na de manoeuvre moet de zeilstand juist zijn.
- Met name het vieren van de schoot moet snel gebeuren.'),

(9, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Afvaren van hoger wal', 
'- Met de kop (nagenoeg) in de wind gaan liggen, Zonodig verhalen.
- Iemand/iets ervoor zorg laten dragen dat het schip niet tegen de wal komt.
- Landvast(en) losmaken, opschieten en paraat opbergen.
- Bemanning evenredig over SB en BB verdelen.
- Stuurman aan de helmstok aan de toekomstige loefzijde.
- Schoten goed los.
- Goed uitkijken voor een veilige afvaart.
- Afzet van de wal naar de gewenste (grootste hoek schip/wal) richting (bij langswal ook vooruit) of recht achteruit.
- Zonodig fok bak.
- Afduwer gaat aan de loefzijde van de fok naar de kuip.'),

(10, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Aankomen aan hoger wal (onder toezicht)', 
'In principe aan de wind aankomen, Een stukje tegen de wind in ‘opschieten’ is toegestaan, De snelheid wordt geregeld met de zeilen, De instructeur kan aanwijzingen geven om de aanleg veilig te laten geschieden.'),

(11, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Afmeren op de eigen ligplaats', 
'Het schip op de eigen ligplaats kunnen afmeren, Stootkussens zonodig gebruiken om beschadigingen te voorkomen, De juiste knopen en steken moeten worden gebruikt.'),
(12, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','De noodzaak van het reven onderkennen',
'Aan kunnen geven wanneer de noodzaak bestaat om te gaan reven, Dit kunnen aangeven aan de hand van: schip, zeilwater, windkracht en geoefendheid van de bemanning, Het reven zelf hoeft niet gekend te worden.'),

(13, 1, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Toepassing van de reglementen', 
'De uitwijkregels voor het eigen vaargebied kunnen toepassen, Een uitwijk-manoeuvre dient tijdig te worden ingezet, De bemanning mag waarschuwen voor andere scheepvaart.'),

(21, 1, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT', 'Schiemanswerk', 
'De kandidaat moet de volgende knopen en steken kennen en op verzoek kunnen leggen: achtknoop, twee halve steken waarvan de eerste slippend, paalsteek, reefsteek (= platte knoop), het beleggen op klamp, nagel of kikker.
Tevens moet een tros kunnen worden opgeschoten.'),

(22, 1, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT', 'Zeiltermen', 
'De kandidaat moet kunnen aangeven wat bedoeld wordt met de volgende termen: hoger wal, lager wal, bakboord, stuurboord, hoge- en lage zijde, loef- en lijzijde, in de wind, aan de wind, halve wind, ruime wind, voor de wind, oploeven, afvallen, overstag gaan, gijpen, kruisrak, killen van het zeil.'),

(23, 1, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT', 'Onderdelen', 
'Op eigen boot en tuigage in de praktijk en op een tekening minstens 15 onderdelen bij de juiste naam kunnen noemen (naar keuze van de kandidaat), Op de tekening moeten duidelijk minstens 20 verschillende onderdelen voorkomen.'),

(24, 1, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT', 'Veiligheid', 
'De kandidaat moet kunnen aangeven waarom het belangrijk is om bij de omgeslagen boot te blijven, En tevens de eisen kennen die gesteld moeten worden aan een reddingvest.'),

(25, 1, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT', 'Reglementen', 
'De kandidaat moet kunnen omschrijven wat bedoeld wordt met het begrip: klein schip (art, 1.01 lid i), Alleen de bepaling omtrent de lengte.

De kandidaat moet met eigen woorden de strekking van de artikelen 1.04 (voorzorgsmaatregelen) en 1.05 (afwijking reglement) kunnen weergeven.

De volgende regels uit het BPR aan de hand van situatieschetsen kunnen toepassen:
6.02 Uitwijkregels tussen een klein schip en een ander schip.
6.03a lid 2 Zeil - spier - motor.
6.03a lid 3 Kruisende koersen kleine zeilschepen onderling.'),

(26, 1, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT', 'Krachten op het schip en hun gevolgen', 
'De kandidaat moet aan kunnen geven wat de effecten zijn van de fok en grootzeil op het sturen van het schip, Ook aan kunnen geven wat er gebeurd bij een onjuiste zeilstand.'),


(31, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Het schip zeilklaar en nachtklaar maken', 
'- Controle inventaris.
- Eventueel schip schoon/droog maken.
- Zeilkle(e)d(en) eraf: droge zijde droog houdend opvouwen en opbergen.
- Zonodig sluitingen controleren.
- Kraanlijn aanslaan.
- Kraanlijn doorzetten.
- Mik, schaar, bok (dan wel stoeltje) onder giek uit en veilig opbergen.
Fok aanslaan:
Val van tevoren klaar hangen, Schoot aan fok bevestigen dan wel klaarleggen, Halshoek vastmaken, Leuvers van onder af aanslaan, Niet in het water laten komen, Zie verder: fok opdoeken, Fokkeschoten door de leiogen en achtknoop er opzetten.
- Grootzeilbindsels vastmaken/controleren (*), Aanslaan: piekeval aan spruit en spruitloperborglijn (*), Klauwval aanslaan (*), Grootzeilval aanslaan (*).
- Zonodig reven.
- Zelflozers (indien aanwezig) naar wens instellen.
- Bemanning moet goed gekleed zijn en de mogelijkheid hebben zich anders te kleden als de omstandigheden veranderen
- Reddingvest voor elk persoon is aan boord mee en is bij voorkeur aangetrokken als een onderdeel van de regenkleding.
(*) indien van toepassing'),

(32, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Verhalen van het schip', 
'Zonder gebruik te maken van de motor, Alle manieren met spierkracht zijn toegelaten met dien verstande dat het verhalen geen gevaar mag opleveren voor bemanning, materiaal of andere scheepvaart, Op het schip zelf dient zo veel mogelijk vanuit de kuip gewerkt te worden.'),

(33, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Stilliggend hijsen en strijken van de zeilen', 
'Met de kop (nagenoeg) in de wind gaan liggen, Zonodig verhalen, Iemand/iets ervoor zorg laten dragen dat het schip niet tegen de wal komt, Bemanning voorin of aan de kant van de kraanlijn plaats laten nemen.
Grootzeil hijsen: Grootschoot los, Zeilbandjes los, Zonodig zeil vangen, Gaffel tot ongeveer 45 graden (*), Vallen samen (*), Piekeval tijdelijk vastzetten (*), Klauwval vastzetten (*), Halstalie vast, Rijglijn/rakbanden zonodig corrigeren, Piek stellen zodat een plooi van nok naar hals resteert (*), Kraanlijn zodanig los dat het zeil er geen hinder van ondervindt.
Fok hijsen: Val losmaken, Zonodig naar de kuip gaan, Schoothoek aan schoot lostrekken (val ontspannen en beheerst trekken), Fok hijsen, Strietsen (dwars op de val trekken; de ruimte die ontstaat over de korvijnagel of kikker met de andere hand wegnemen), Val beleggen, Vallen en kraanlijn opschieten.
(*) alleen voor gaffelzeilen.'),

(34, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Stand en bediening van de zeilen', 
'Zowel bij het varen van een rechte koers als bij het maken van bochten dient steeds zoveel mogelijk de juiste zeilstand te worden gevoerd.
De zeilen dienen steeds zoveel mogelijk gevierd te zijn zonder dat het voorlijk daarbij kilt, Bij oploeven is het killen van de fok en bij afvallen is het killen van het grootzeil in bescheiden mate noodzakelijk, De zeilen moeten het sturen van de boot ondersteunen.'),

(35, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Sturen, roer- en schootbediening', 
'Het schip met behulp van het roer en de zeilen een rechte koers en bochten kunnen laten varen, zodanig dat een aangewezen punt zonder onnodige omwegen wordt aangezeild.'),

(36, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Overstag gaan', 
'Van hoog aan de wind over de ene boeg naar hoog aan de wind over de andere boeg, Als er niet hoog aan de wind wordt gevaren, kan een opdraaiende beweging worden gemaakt waarbij vloeiend wordt overgegaan in de overstagmanoeuvre.
Commando’s:
“Klaar om te wenden”: waarschuwingscommando, Indien nodig ook te gebruiken in sloten en kanalen, Bemanning maakt zich gereed.
“Ree”: start van de manoeuvre, Bemanning laat de fokkeschoot 10 tot 15 cm vieren (= fok killend bij), Grootschoot zonodig en zo mogelijke enige decimeters aantrekken
“Fok bak”: alleen te gebruiken als de sturing van het schip het nodig maakt, Als de boot nagenoeg in de wind ligt, Bemanning trekt de fokkeschoot aan de oude loefzijde weer aan.
“Fok over”: als de boot net door de wind heen is, Bemanning viert de ‘oude’ fokkeschoot op en trekt de ‘nieuwe’ fokkeschoot aan totdat de schoothoek net niet meer klappert.
“Fok aan”: als de boot weer wat snelheid heeft gekregen, De bemanning zet de fok strak, Dit moet zonder ‘rukken’ gebeuren, In de draai moet de fokkeschoot zoveel aangetrokken worden dat de fok geen wind vangt maar dat het klapperen belemmerd wordt.
Zo weinig mogelijk roer geven (alleen bij heel weinig wind of veel golfslag is meer roer geven noodzakelijk), Stuurman met het gezicht naar voren gaan verzitten.'),

(37, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Opkruisen in nauw vaarwater', 
'Goed hoog aan de wind zeilen en rekening houden met het andere scheepvaartverkeer, Wanneer de wind van ??n van de oevers waait, zal het in nauw vaarwater noodzakelijk zijn de korte slag met een knik in de schoot te varen teneinde voldoende snelheid te krijgen voor een vloeiende
overstagmanoeuvre.'),

(38, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Gijpen en gijpen kunnen vermijden', 
'- Aan zien komen wanneer er gegijpt moet worden.
- De stuurman attendeert de bemanning op de komende gijp.
- Het overkomen van het zeil moet pal voor de wind gebeuren.
- Na de gijp zit de stuurman aan de hoge zijde.
- Het schip moet een vloeiende, zo nodig gestrekte, koers blijven varen.
- ‘Nieuwe’ fokkeschoot wordt gepakt.
- Eventueel opnieuw fok te loevert zetten.
- Direct voor en na de manoeuvre moet de zeilstand juist zijn, Met name het vieren van de schoot moet snel gebeuren.
Gijpen vermijden:
Indien de omstandigheden het noodzakelijk maken moet een gijp vermeden kunnen worden, Bijvoorbeeld het vervangen van de gijp door het maken van een ‘stormrondje’, Bij een ‘stormrondje’ dient rustig te worden opgeloefd en na de overstagmanoeuvre vlot te worden afgevallen door het grootzeil flink los te zetten en de fok bak te blijven houden.
Het strijken van het grootzeil is ook een mogelijkheid om de ‘gijp’ (althans met het grootzeil) te vermijden.'),

(39, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Afvaren van hoger wal', 
'- Met de kop (nagenoeg) in de wind gaan liggen.
- Zonodig verhalen.
- Iemand/iets ervoor zorg laten dragen dat het schip niet tegen de wal komt.
- Landvast(en) losmaken, opschieten en paraat opbergen.
- Bemanning evenredig over sb en bb verdelen, Stuurman aan de helmstok aan de toekomstige loefzijde.
- Schoten goed los.
- Goed uitkijken voor een veilige afvaart.
- Afzet van de wal naar de gewenste (grootste hoek schip/wal) richting (bij langswal ook vooruit) of recht achteruit.
- Zonodig fok bak.
- Afduwer gaat aan de loefzijde van de fok naar de kuip.
- Zonodig moet er worden gedeinsd.
Deinzen:
- Schip in de wind leggen.
- Bemanningsgewicht evenredig over stuur- en bakboord verdelen.
- Schoten goed los.
- Fok zo mogelijk bundelen.
- Stuurman aan de toekomstige loefzijde.
- Afduwer houdt schip aan de voorstag of aan de randen van het voordek vast.
- Het been dat het dichtst bij het voorstag is, wordt op het schip geplaatst.
- Afzet krachtig en recht achteruit.
- Roerganger geeft roer voor deinzend schip.
- Volvallen over de van tevoren vastgestelde boeg, Bij voorkeur zonder fok bak.
- Helmstok/hout niet loslaten.
- Vaart gaan maken (zeil aantrekken) zodra het schip op de juiste koers ligt.'),

(40, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Aankomen aan hoger wal (onder alle omstandigheden)', 
'De aankomst aan hoger wal dient ook zonder een ‘dwarspeiling’ te kunnen worden uitgevoerd.
- Landvasten gereed leggen/houden en vastmaken aan het schip.
- Schip moet stilliggen vlak voor de op de wal aangegeven plaats op een der aan de windse koersen (zonodig afhouden op veilige wijze).
- Het schip moet zoveel mogelijk loodrecht op de wal aankomen.
- De snelheidsregeling moet zichtbaar zijn, De controle op volledige killende zeilen (op de juiste koers varend) moet hebben plaatsgevonden.
- Het bemanningslid dat vast gaat maken, blijft zo lang mogelijk ‘laag’ en houdt zich gereed met het landvast in de hand, Via de loefzijde aan de wal stappen (niet springen)'),

(41, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Afmeren van het schip', 
'Schip dusdanig fixeren dat ook op lange termijn schade aan eigen of andere schepen niet mogelijk is.
- Gebruik zo min mogelijk verbindingslijnen met de ‘wal’ (minder dan drie en meer dan zes is altijd fout).
- Kies de lijn zo lang mogelijk.
- Eerst die lijnen vastmaken die de natuurlijke beweging van het schip tegengaan (in de wind of
tegenstrooms).'),

(42, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Kunnen reven op het eigen schip', 
'Aan kunnen geven wanneer de noodzaak bestaat om te gaan reven, Dit kunnen aangeven aan de hand van: schip, zeilwater, windkracht en geoefendheid van de bemanning, Op de eigen boot moet, indien noodzakelijk, gereefd kunnen worden.'),

(43, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Toepassing van de reglementen', 
'De uitwijkregels voor het eigen vaargebied kunnen toepassen, Een uitwijk-manoeuvre dient tijdig te worden ingezet, De bemanning mag waarschuwen voor andere scheepvaart.'),

(44, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Man-over-boordmanoeuvre', 
'- “Man over boord” constateren en roepen, “Zwem” toeroepen.
- Zonodig een drijfmiddel toewerpen.
- Op elke willekeurige koers afvallen naar voor de wind.
- Er dient iemand te wijzen als de drenkeling moeilijk zichtbaar is.
- Voor de wind varen totdat je over de aan de windse lijn heen bent (ongeveer 4 bootlengtes).
- Oploeven en aan de wind gaan varen.
- Stuurman constateert of laat constateren: “man dwars”.
- Overstag, snelheid regelen (niet stil gaan liggen) en langzaam aan lij van de drenkeling
langsvaren.
- Bemanning geeft aanwijzingen voor de koers in de laatste meters.
- Bemanning staat aan loef klaar om drenkeling vast te pakken.
- Bemanning roept “man vast” als dat het geval is.
- Fok wordt bak getrokken.
- Drenkeling aan loef, op het draaipunt van het schip (achter het want), zijdelings en zo horizontaal mogelijk binnenhalen, 
- Bijliggen.
- EHBO toepassen.'),

(45, 2, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Loskomen van aan de grond', 
'- In volgorde van de moeilijkheid van de situatie, als je constateert dat je vastloopt, dien je: 
- Zo snel mogelijk van de ondiepte af te sturen.
- Het schip te krengen om de diepgang te verminderen (denk aan de gijp in voor de windse situaties).
- De vaarboom erbij te nemen en: a, door de wind bomen en wegvaren b, een gijp te forceren en wegvaren.
- Het zeil te strijken en de boot via dezelfde weg terug te duwen (of zonodig te laten slepen) als dat je op de ondiepte bent gekomen.'),


(51, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Schiemanswerk', 
'De kandidaat moet de volgende steken bij naam kennen en op verzoek kunnen leggen:
Twee halve steken (waarvan de eerste slippend), achtknoop, paalsteek, platte knoop, mastworp (met slipsteek als borg), schootsteek (enkel), Tevens moet de functie van deze knopen en steken gekend te worden, Tevens: een lijn juist kunnen opschieten en een lijn goed kunnen beleggen op een kikker.'),

(52, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Zeiltermen', 
'De kandidaat moet kunnen aangeven wat bedoeld wordt met de volgende termen:
Hoger wal, lager wal, bakboord, stuurboord, hoge- en lage zijde, loef- en lijzijde, in de wind, aan de wind, halve wind, ruime wind, voor de wind, oploeven, afvallen, overstag gaan, gijpen, kruisrak, killen van het zeil, deinzen, opschieten, beleggen.'),

(53, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Onderdelen', 
'Van de eigen boot en tuigage in de praktijk en op een tekening minstens 25 onderdelen bij de juiste naam kunnen noemen, Deze onderdelen naar eigen keuzen van de kandidaat, Op de tekening moeten duidelijk minstens 30 verschillende onderdelen voorkomen, In ieder geval moeten gekend worden: blok, landvast, kiel, helmstok, roer, mast, giek, val, schoot, halshoek, schoothoek, grootzeil, fok.'),

(54, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Veiligheid', 
'De kandidaat moet kunnen aangeven waarom het belangrijk is om bij de omgeslagen boot te blijven, En tevens de eisen kennen die gesteld moeten worden aan een reddingvest.'),

(55, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Reglementen', 
'Kunnen omschrijven wat bedoeld wordt met de begrippen: zeilschip, motorschip (art, 1.01 lid b, b1), klein schip (art, 1.01 lid i, alleen de bepaling over de lengte), tegengestelde koersen, oplopen (art, 6.01), De kandidaat moet met eigen woorden de strekking van de artikelen 1.04 (voorzorgsmaatregelen) en 1.05 (afwijking reglement) kunnen weergeven.

De volgende regels uit het BPR aan de hand van situatieschetsen kunnen toepassen: 
6.02 Uitwijkregels tussen een klein schip en een ander schip, 
6.03 lid 1, 3 en 4 Algemene beginselen bij ontmoeten.
6.03a lid 2 Zeil - spier - motor.
6.03a lid 3 Kruisende koersen kleine zeilschepen onderling.
6.03a lid 4 Kruisende koersen ontstaan door oversteken e.d.
6.04 lid 2 en 3 Tegengestelde koersen.
6.10 lid 1 en 2 Oplopen; voorbijlopen.

De kandidaat moet weten dat naast het BPR nog andere reglementen kunnen gelden en weten waar het BPR en deze andere reglementen gevonden kunnen worden.'),

(56, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Krachten op het schip en hun gevolgen', 
'Aan kunnen geven wat de effecten zijn van fok en grootzeil op het sturen van het schip, Ook aan kunnen geven wat er gebeurd bij een onjuiste zeilstand, Aan kunnen geven wat de effecten zijn van de helling van de boot op het sturen van het schip.'),

(57, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Gedragsregels', 
'De goede gebruiken ten opzichte van andere watersporters, waaronder wedstrijdzeilers, kennen, De verantwoording kennen ten opzichte van het milieu.'),

(58, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Weersinvloeden', 
'Het kunnen interpreteren van het weerbericht met betrekking tot de veiligheid van het kielboot varen, mede gezien de eigen vaardigheid, Het tijdig kunnen herkennen van voortekenen van plotselinge weersomslagen zoals onweer en zware windvlagen.'),

(59, 2, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Vaarproblematiek andersoortige schepen', 
'Het gevaar kennen van de dode hoek en de zuiging van grote schepen, Weten dat grote schepen (onder andere ten gevolge van hun diepgang) op smal vaarwater niet kunnen wijken, Weten dat ook grote vrachtschepen sterk kunnen verlijeren'),

(61, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Het aanslaan van de zeilen', 
'Een zeil kunnen aanslaan aan de rondhouten van het ‘eigen’ schip.'),

(62, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Het schip zeilklaar maken en klaarmaken voor de nacht', 
'- Controle inventaris.
- Eventueel schip schoon/droog maken.
- Zeilkle(e)d(en) eraf: droge zijde droog houdend opvouwen en opbergen.
- Zonodig sluitingen controleren.
- Kraanlijn aanslaan.
- Kraanlijn doorzetten.
- Mik, schaar, bok (dan wel stoeltje) onder giek uit en veilig opbergen.
Fok aanslaan: Val van tevoren klaar hangen, Schoot aan fok bevestigen dan wel klaarleggen, Halshoek vastmaken, Leuvers van onder af aanslaan, Niet in het water laten komen, Zie verder: fok opdoeken, Fokkeschoten door de leiogen en achtknoop erop zetten.
- Grootzeilbindsels vastmaken/controleren (*), Aanslaan: Piekeval aan spruit en spruitloperborglijn (*).Klauwval aanslaan (*), Grootzeilval aanslaan (*).
- Zonodig reven.
- Zelflozers (indien aanwezig) naar wens instellen.
- Bemanning moet goed gekleed zijn en de mogelijkheid hebben zich anders te kleden als de omstandigheden veranderen.
- Reddingvest voor elk persoon is aan boord mee en is bij voorkeur aangetrokken als een onderdeel van de regenkleding.
(*) indien van toepassing'),

(63, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Verhalen van het schip', 
'Zonder gebruik te maken van de motor, Alle manieren met spierkracht zijn toegelaten met dien verstande dat het verhalen geen gevaar op mag leveren voor bemanning, materiaal of andere scheepvaart, Op het schip zelf dient zo veel mogelijk vanuit de kuip gewerkt te worden.'),

(64, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Hijsen en strijken van de zeilen', 
'Stilliggend:
Met de kop (nagenoeg) in de wind gaan liggen, Zo nodig verhalen, Iemand/iets ervoor zorg laten dragen dat het schip niet tegen de wal komt, Bemanning voorin of aan
de kant van de kraanlijn plaats laten nemen.
Grootzeil hijsen: Grootschoot los, Zeilbandjes los, Zonodig zeil opvangen, Gaffel tot ongeveer 45 graden (*), Vallen samen (*), Piekeval tijdelijk vastzetten (*), Klauwval vastzetten (*), Halstalie vast, Rijglijn/rakbanden zonodig corrigeren, Piek stellen zodat een plooi van nok naar hals resteert (*), Kraanlijn zodanig los dat het zeil er geen hinder van ondervindt.
Fok hijsen: Val losmaken, Zonodig naar de kuip gaan, Schoothoek aan schoot lostrekken (val ontspannen en beheerst trekken), Strietsen (dwars op de val trekken; de ruimte die ontstaat over de korvijnagel of kikker met de andere hand wegnemen), Val beleggen, Vallen/kraanlijn opschieten.
(*) alleen voor gaffelzeilen

Varend:
Voorbereiding: Fokkeval vastmaken aan nagelbank/knecht, Nog ??n zeilbandje vast met slipsteek, Kraanlijn strak aan toekomstige loefzijde, Schoot met slipsteek gereed om snel los te maken, Fokkeschoot klaarleggen naar stuurman toe, Grootzeilval(len) in de hand nemen (als het grootzeil eerst gehesen wordt).
Uitvoering in principe: Stuurman gaat aan toekomstige loefzijde zitten, Bij alle koersen hoger dan halve wind eerst grootzeil en dan de fok, Bij andere koersen eerst fok, vaart lopen, oploeven tot aan de wind en grootzeil hijsen, (Zie voor het hijsen: stilliggend).
Let op: Piekeval sterker doorzetten dan 45 graden, dan wel alleen de piek hijsen en met de hand voor schoothoek spelen.
Uitzonderingen: Bij luwtes/weinig wind, vaak bij bruggen, kan het grootzeil ook gehesen worden bij ruimere koers.
Veiligheid: Let goed op het andere scheepvaartverkeer.'),

(65, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Stand en bediening van de zeilen', 
'Zowel bij het varen van een rechte koers als bij het maken van bochten dient steeds zoveel mogelijk de juiste zeilstand te worden gevoerd, De zeilen dienen zoveel mogelijk gevierd te zijn zonder dat het voorlijk daarbij kilt, Bij oploeven is het killen van de fok en bij afvallen is het killen van het grootzeil in bescheiden mate noodzakelijk, De zeilen moeten het sturen van de boot ondersteunen.'),

(66, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Bovenwinds gelegen punt kunnen bezeilen', 
'Met zo min mogelijk slagen een in de wind gelegen punt kunnen bezeilen, Daarbij goed kunnen bepalen wanneer er overstag gegaan kan worden door het gebruik van de ‘achterlijker dan dwars’-peiling, Wanneer een lange en een korte slag gemaakt moeten worden, bij voorkeur met de korte slag bij het in de windse punt aankomen.'),

(67, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Opkruisen in nauw vaarwater', 
'Goed hoog aan de wind zeilen en rekening houden met het andere scheep-vaartverkeer, Wanneer de wind van ??n van de oevers waait, zal het in nauw vaarwater noodzakelijk zijn de korte slag met een knik in de schoot te varen teneinde voldoende snelheid te krijgen voor een vloeiende
overstagmanoeuvre.'),

(68, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Gijpen', 
'- Aan zien komen wanneer er gegijpt moet worden.
- De stuurman attendeert de bemanning op de komende gijp.
- Het overkomen van het zeil moet pal voor de wind gebeuren.
- Na de gijp zit de stuurman aan de hoge zijde.
- Het schip moet een vloeiende, zonodig gestrekte, koers blijven varen.
- ‘Nieuwe’ fokkeschoot wordt gepakt.
- Eventueel opnieuw fok te loevert zetten.
- Direct voor en na de manoeuvre moet de zeilstand juist zijn, Met name het vieren van de schoot moet snel gebeuren.

Gijpen vermijden:
Indien de omstandigheden het noodzakelijk maken moet een gijp vermeden kunnen worden, Bijvoorbeeld het vervangen van de gijp door het maken van een ‘stormrondje’, Bij een ‘stormrondje’ dient rustig te worden opgeloefd en na de overstagmanoeuvre vlot te worden afgevallen door het grootzeil flink los te zetten en de fok bak te blijven houden, Het strijken van het grootzeil is ook een mogelijkheid om de ‘gijp’ (althans met het grootzeil) te vermijden.'),

(69, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Afvaren van en aankomen aan hoger wal', 
'Afvaren van hoger wal:
- Met de kop (nagenoeg) in de wind gaan liggen.
- Zonodig verhalen.
- Iemand/iets ervoor zorg laten dragen dat het schip niet tegen de wal komt.
- Landvast(en) losmaken, opschieten en paraat opbergen.
- Bemanning evenredig over sb en bb verdelen.
- Stuurman aan de helmstok aan de toekomstige loefzijde.
- Schoten goed los.
- Goed uitkijken voor een veilige afvaart.
- Afzet van de wal naar de gewenste (grootste hoek schip/wal) richting (bij langswal ook vooruit) of recht achteruit.
- Zonodig fok bak.
- Afduwer gaat aan de loefzijde van de fok naar de kuip.
- Zonodig moet er worden gedeinsd.

Deinzen:
- Schip in de wind leggen.
- Bemanningsgewicht evenredig over stuur- en bakboord verdelen.
- Schoten goed los.
- Fok zo mogelijk bundelen.
- Stuurman aan de toekomstige loefzijde.
- Afduwer houdt het schip aan de voorstag of aan de randen van het voordek vast.
- Het been dat het dichtst bij het voorstag is wordt op het schip geplaatst.
- Afzet krachtig en recht achteruit.
- Roerganger geeft roer voor deinzend schip.
- Volvallen over de van tevoren vastgestelde boeg, Bij voorkeur zonder fok bak.
- Helmstok/hout niet loslaten.
- Vaart gaan maken (zeil aantrekken) zodra het schip op de juiste koers ligt.

Aankomen aan hoger wal onder alle omstandigheden:
- De aankomst aan hoger wal dient ook zonder een ‘dwarspeiling’ te kunnen worden uitgevoerd.
- Landvasten gereed leggen/houden en vastmaken aan het schip.
- Schip moet stilliggen vlak voor de op de wal aangegeven plaats op ??n der aan de windse koersen (zonodig afhouden op veilige wijze).
- Het schip moet zoveel mogelijk loodrecht op de wal aankomen.
- De snelheidsregeling moet zichtbaar zijn, De controle op volledige killende zeilen (op de juiste koers varend) moet hebben plaatsgevonden.
- Het bemanningslid dat vast gaat maken, blijft zo lang mogelijk ‘laag’ en houdt zich gereed met het landvast in de hand, Via de loefzijde aan de wal stappen (niet springen).'),

(70, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Man-over-boordmanoeuvre', 
'- “Man over boord” constateren en roepen.
- “Zwem” toeroepen.
- Zonodig een drijfmiddel toewerpen.
- Op elke willekeurige koers afvallen naar voor de wind.
- Er dient iemand te wijzen als de drenkeling moeilijk zichtbaar is.
- Voor de wind varen totdat je over de aan de windse lijn heen bent (ongeveer 4 bootlengtes).
- Oploeven en aan de wind gaan varen.
- Stuurman constateert of laat constateren: “man dwars”.
- Overstag.
- Snelheid regelen (niet stil gaan liggen) en langzaam aan lij van de drenkeling
langsvaren.
- Bemanning geeft aanwijzingen voor de koers in de laatste meters.
- Bemanning staat aan loef klaar om drenkeling vast te pakken.
- Bemanning roept “man vast” als dat het geval is.
- Fok wordt bak getrokken.
- Drenkeling aan loef, op het draaipunt van het schip (achter het want), zijdelings en zo horizontaal mogelijk binnenhalen.
- Bijliggen.
- EHBO toepassen.'),

(71, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Aankomen aan lager wal', 
'Voorbereiding: Stootwillen op de juiste plaats bevestigen en zo mogelijk terug in het schip leggen, Afstoplijn zonodig gereed maken en beleggen in de buurt van het draaipunt van het schip, Vallen klaar maken voor het vrij uitlopen tijdens het strijken, Kraanlijn aan toekomstige loefzijde, Zeilbandjes gereed houden.
Uitvoering: De keuze van het al dan niet eerst strijken van de fok hangt af van de bekwaamheid van de bemanning en de bestuurbaarheid van het schip, Fok zonodig strijken, Grootzeil bovenwinds strijken op aan de windse koers.
Grootzeil strijken: Voorstrijk (vallen 20 cm vieren), Grootschoot vast, Vlot strijken, Grootzeil aan loef binnenhalen, Zeilbandjes vast.
Fok strijken: Niet in het water laten komen, Fok opdoeken, Stootwillen uithangen.
Bij aankomst:
a, via opdraaimethode: vaart verminderen door tegen de wind in te sturen
b, via afstopmethode: afstoppen met afstoplijn.
Veiligheid: Schip ‘vierkant’ houden, De bemanning niet aan de lijzijde achterin de kuip, Werkende en meevarende bemanning zo snel mogelijk laag in de kuip plaats laten nemen, Het uitzicht van de stuurman wordt belemmerd, dus de bemanning moet mee uitkijken, De situatie moet zo kort mogelijk duren, dus zo snel mogelijk uitvoeren, Niet met de handen of voeten vanaf het schip afhouden, Wel goed: afstappen en schip afhouden.'),

(72, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Afmeren van het eigen schip', 
'- Schip dusdanig fixeren dat ook op lange termijn schade aan eigen of andere schepen niet mogelijk is.
- Gebruik zo min mogelijk verbindingslijnen met de ‘wal’ (minder dan drie en meer dan zes is altijd fout).
- Kies de lijn zo lang mogelijk.
- Eerst die lijnen vastmaken die de natuurlijke beweging van het schip tegengaan (in de wind of tegenstrooms).'),

(73, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Kunnen reven op het eigen schip', 
'Aan kunnen geven wanneer de noodzaak bestaat om te gaan reven, Dit aangeven aan de hand van: schip, zeilwater, windkrachten geoefendheid van de bemanning, Op de eigen lesboot moet indien noodzakelijk gereefd kunnen worden.'),

(74, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Eenvoudig ankeren', 
'De kandidaat moet in een noodgeval gebruik kunnen maken van het aanwezige anker, Rekening moet worden gehouden met: geen lijn(en) om het anker, het anker moet zich in kunnen graven, het schip moet (nagenoeg) in de wind blijven liggen tijdens het ankeren.'),

(75, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Eenvoudige zeil- en scheepstrim', 
'De kandidaat moet de functie van de bolling van het zeil kennen en zonodig kunnen be¥nvloeden, Tevens moet de helling van het schip steeds zoveel mogelijk constant blijven (een ietsje naar lij).'),

(76, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Loskomen van aan de grond', 
'In volgorde van de moeilijkheid van de situatie, als je constateert dat je vastloopt, dien je:
- Zo snel mogelijk van de ondiepte af te sturen.
- Het schip te krengen om de diepgang te verminderen (denk aan de gijp in voor de windse situaties).
- De vaarboom erbij te nemen en: a, door de wind bomen en wegvaren b, een gijp te forceren en wegvaren.
- Het zeil te strijken en de boot via dezelfde weg terug te duwen (of zonodig te laten slepen) als dat je op de ondiepte bent gekomen.'),

(77, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Bedienen van een binnen- of buitenboordmotor (NVT)', 
'De kandidaat wordt verondersteld met tenminste één motor om te kunnen gaan, De kandidaat moet het brandstof- en oliepeil kunnen controleren en zonodig bijvullen, De start en stopprocedure van de motor moet gekend worden (zonodig het gebruik van de choke kennen), Het gebruik van de keerkoppeling moet bekend zijn, Bij buitenboordmotoren moet gecontroleerd worden of er gevaar bestaat voor het raken van de schroef door het roer.'),

(78, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Schiemanswerk', 
'Toepassing en onderhoud van touwwerk:
De gebruiksmogelijkheden kennen van verschillende soorten touwwerk (kunststof) voor landvasten, vallen, sleeplijn en ankerlijn, Het touwwerk moet vrij van zand en scherpe randen worden gehouden en zoveel mogelijk gevrijwaard zijn van invloed van UV-licht.
Steken en knopen en hun toepassing:
Twee halve steken, slipsteek, achtknoop, platte knoop, schootsteek (enkel en dubbel), mastworp (2 manieren), paalsteek, opschieten van een tros, tros beleggen op een bolder, lijn beleggen op een klamp of nagel.'),

(79, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Aanvarings-/achtergrondspeiling kunnen maken', 
'Kunnen vaststellen of er gevaar voor een aanvaring zal ontstaan bij kruisende koersen door over het andere schip een peiling te nemen op de achtergrond.'),

(80, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Toepassing van de reglementen', 
'De uitwijkregels voor het eigen vaargebied kunnen toepassen, Een uitwijkmanoeuvre dient tijdig te worden ingezet, De bemanning mag waarschuwen voor andere scheepvaart.'),

(81, 3, 'praktijk', CURRENT_TIMESTAMP(), 'IMPORT','Terminologie', 
'Zoveel mogelijk dient de juiste naamgeving te worden gebruikt, Zowel bij de communicatie binnen de boot als tussen schepen en personen onderling.'),

(91, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Schiemanswerk', 
'De kandidaat moet de volgende steken bij naam kennen en op verzoek kunnen leggen:
Twee halve steken, waarvan de eerste slippend, achtknoop, paalsteek, platte knoop, mastworp (met slipsteek als borg), schootsteek (enkel), mastworp op twee manieren, (met slipsteek als borg), schootsteek op twee manieren (enkel en dubbel).
Ook dient de functie van deze knopen en steken gekend te worden, En tevens: een lijn juist kunnen opschieten en een lijn goed kunnen beleggen op een kikker, Een lijn goed kunnen beleggen op een bolder.
De kandidaat moet kunnen aangeven dat touwsoorten kunnen verschillen in: rekvermogen, breeksterkte, slijtvastheid, wateropname en UV-bestendigheid, Het verschil tussen geslagen en gevlochten touwwerk moet herkend worden, De kandidaat moet daarbij het verschil kunnen aangeven tussen diverse soorten kunstvezeltouw, De gebruiksmogelijkheden van verschillende soorten touwwerk voor landvasten, vallen, schoten, sleeplijn en ankerlijn moeten gekend worden, De kandidaat moet weten dat touwwerk vrij van zand gehouden moet worden en zoveel mogelijk gevrijwaard van UV-licht, Het begrip schavielen en maatregelen daartegen moeten beschreven kunnen worden.'),

(92, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Zeiltermen', 
'De kandidaat moet kunnen aangeven wat bedoeld wordt met de volgende termen:
Hoger wal, lager wal, bakboord, stuurboord, hoge- en lage zijde, loef- en lijzijde, in de wind, aan de wind, halve wind, ruime wind, voor de wind, oploeven, afvallen, overstag gaan, gijpen, kruisrak, killen van het zeil, deinzen, opschieten, beleggen, bovenlangs, onderlangs, dwarspeiling, bezeild, binnen de wind, korte slag, lange slag, opschieter, zuigen, duiken, planeren, volvallen, verhalen, verlijeren, drift, bijliggen, bak(-houden).'),

(93, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Onderdelen', 
'Van de eigen boot en tuigage in de praktijk en op afbeeldingen minstens 40 onderdelen bij de juiste naam kunnen noemen, Deze onderdelen naar eigen keuze van de kandidaat, In ieder geval moeten gekend worden:
Voorsteven, spiegel, sluiting, kous, blok, stootkussen, hoosvat, landvast, kiel, helmstok, roer, roerblad, mast, giek, val, halstalie, schoot, voor-, achter-, onderlijk, hals-, schoothoek, grootzeil, fok.'),

(94, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Veiligheid', 
'Kunnen aangeven waarom het belangrijk is om bij de omgeslagen boot te blijven, De eisen kennen die gesteld moeten worden aan een zwemvest (reddingvest).'),

(95, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Reglementen', 
'De kandidaat moet kunnen omschrijven wat bedoeld wordt met de begrippen:
Schip, motorschip, zeilschip, sleep, assisteren, veerpont, klein schip (geheel), des nachts, des daags, korte en lange stoot, vaarweg, vaarwater (art 1.01 lid a, b, b1, c, c1, h, i, n, o, s, v, w), toplicht, boordlichten, heklicht, rondom schijnend licht (art 3.01a lid a, b, c, d), tegengestelde koersen, oplopen (art 6.01).

De kandidaat moet met eigen woorden de strekking van de artikelen 1.04 (voorzorgsmaatregelen) en 1.05 (afwijking reglement) kunnen weergeven.
De kandidaat moet weten welke verplichtingen er volgens het BPR rusten op de schipper en aan welke voorwaarden de roerganger van een schip moet voldoen (1.02 en 1.09).

De kandidaat moet bovendien bekend zijn met de volgende artikelen uit het BPR:
1.11 Verplichting reglement aan boord te hebben.
2.02 Kentekens kleine schepen.
7.09 Gedogen langszij komen.
7.10 Meewerken bij vertrekken, verhalen etc.

De kandidaat moet de eisen voor de lichten en dagtekens kennen zoals gesteld in de volgende artikelen en schepen aan hun lichten of dagtekens kunnen herkennen:
3.05 Verboden tekens.
3.07 Verboden lichten of tekens.
3.08 lid 1 Lichten motorschepen.
3.09 lid 1,2,3,4,7 Lichten motorschepen die assisteren, dagtekens slepen etc.
3.12 Lichten zeilschepen.
3.13 Lichten kleine schepen, dagteken klein schip varend met motor en zeil.
3.20 Lichten stilliggende schepen, dagteken geankerd schip.
3.25 Lichten drijvende werktuigen e.d., dagtekens drijvende werktuigen e.d.

De kandidaat moet kennis hebben van de volgende artikelen over het geven van geluidseinen 4.01 lid 1b en 4, 4.02 en 4.04 en de betekenis kennen van de in bijlage 6 onder A genoemde seinen, Ook moeten de noodsignalen zoals genoemd in 3.30 bekend zijn.
Bijlage 6 deel A: Algemene geluidseinen:
- Attentie.
- Ik ga stuurboord uit.
- Ik ga bakboord uit.
- Ik sla achteruit.
- Ik kan niet manoeuvreren.
- Noodsein.
- Blijf weg sein.
- Verzoek tot bedienen van brug of van een sluis.

Bovendien moet de kandidaat kennis hebben van artikel 5.05 omtrent verkeerstekens en van de volgende tekens uit bijlage 7: A.9, A.13, A.15, B.10, E.16 en E.18.
Bijlage 7: Verkeerstekens (Algemeen):
A.9 Verboden hinderlijke waterbeweging.
A.13 Verboden voor kleine schepen.
A.15 Verboden voor zeilschepen.
B.10 Verplichting zonodig koers en snelheid te wijzigen t.b.v, uitvarende schepen.
E.16 Kleine schepen toegestaan.
E.18 Zeilschepen toegestaan.

De kandidaat moet kennis hebben van de artikelen 3.29 en 3.38 (tekens bescherming hinderlijke waterbeweging en duikers) en de daarin genoemde tekens.

De kandidaat moet kennis hebben van de reglementen aangaande het in- en uitvaren van sluizen en het doorvaren van bruggen en sluizen (6.26, 6.28 - 2bis, 3 en 7, 6.28a) en de betekenis kennen van de lichten en tekens die daarvoor van belang zijn (Bijlage 7 - A.1, A.11, B.5, D.1, E.1, G.1, G.2, G.4, G5.1a, H.3).
Bijlage 7: Verkeerstekens (bij bruggen en sluizen)
A.1 In-, uit- of doorvaren verboden.
A.11 Bruglichten.
B.5 Verplichting voor het bord stil te houden.
D.1 Aanbevolen doorvaartopening vaste bruggen.
E.1 In-, uit- of doorvaart toegestaan.
G.1 Optische tekens bij vaste bruggen.
G.2 Optische tekens bij beweegbare bruggen.
G.4 Optische tekens bij sluizen.
G.5 Hoogteschaal.
H.3 Overige aanduidingen.

De volgende regels uit het BPR aan de hand van situatieschetsen of vragen kunnen toepassen:
6.02 Uitwijkregels tussen een klein schip en een ander schip.
6.03 - 1,3,4 Algemene beginselen bij ontmoeten.
6.03a - 1 Kruisende koersen, algemeen.
6.03a - 2 Kruisende koersen zeil - spier - motor.
6.03a - 3 Kruisende koersen kleine zeilschepen onderling.
6.03a - 4 Kruisende koersen ontstaan door oversteken e.d.
6.04 - 1, 2, 3 Tegengestelde koersen, ook motorboten onderling.
6.07 Tegengestelde koersen bij een engte.
6.09 Algemene bepalingen voorbijlopen.
6.10 - 1, 2 Oplopen; voorbijlopen.
6.13 - 1, 4 Keren.
6.14 Vertrek.
6.16 - 1, 4, 5 Uitvaren nevenvaarwater, oversteken.
6.17 Op gelijke hoogte varen.
6.20 - 1 Hinderlijke waterbeweging.

De kandidaat moet weten dat naast het BPR nog andere reglementen kunnen gelden en weten waar het BPR en deze andere reglementen gevonden kunnen worden, De kandidaat moet weten waar het BPR geldt (vaststellingsbesluit BPR Art 2) en welke andere reglementen bovendien nog op welke vaarwateren binnen zijn vaargebied gelden.

De kandidaat weet voor het varen met welke schepen een Klein Vaarbewijs verplicht is (Binnenschepenwet Art, 18).'),

(96, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Krachten op het schip en hun gevolgen', 
'De begrippen kracht en koppel moeten gekend worden, De kandidaat moet ze kunnen gebruiken bij het uitleggen van de onderstaande zaken.
Aan kunnen geven wat de effecten zijn van fok en grootzeil op het sturen van het schip, Ook aan kunnen geven wat er gebeurt bij een onjuiste zeilstand, Aan kunnen geven wat de effecten zijn van de helling van de boot op het sturen van het schip, Verklaard moet kunnen worden hoe ten gevolge van de kracht van de wind op het zeil, drift en voortstuwing ontstaan.
De kandidaat moet kennis hebben van de oorzaken van stabiliteit van scherpe jachten, Het verschil tussen gewichtsstabiliteit en vormstabiliteit moet kunnen worden uitgelegd.'),

(97, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Gedragsregels, vlagvoering en jachtetiquette', 
'De goede gebruiken ten opzichte van andere watersporters waaronder wedstrijdzeilers kennen, De verantwoording kennen t.o.v, het milieu.
Het kennen van de vlagvoering van het eigen schip.'),

(98, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Weersinvloeden', 
'Het kunnen interpreteren van het weerbericht met betrekking tot de veiligheid van het kielbootvaren, mede gezien de eigen vaardigheid.
Het tijdig kunnen herkennen van voortekenen van plotselinge weersomslagen zoals onweer en zware windvlagen, Weten welke windsnelheden (in m/sec) horen bij de verschillende stappen van de schaal van Beaufort en omgekeerd, Het verband kennen tussen de omschrijvingen die bij waarschuwingen gebruikt worden en het bovenstaande.'),

(99, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Vaarproblematiek andersoortige schepen', 
'Het gevaar kennen van de dode hoek en de zuiging van grote schepen, Weten dat grote schepen (o.a, ten gevolge van hun diepgang) op smal vaarwater niet uit kunnen wijken, Weten dat ook grote vrachtschepen sterk kunnen
verlijeren.'),

(100, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT', 'Dagelijkes onerhoud van het ‘eigen’ schip', 
'Kennis over: de controle op het vastzitten van bevestigingsmaterialen aan boord (ook boven in de mast), het bijwerken van kleine beschadigingen, het schoonhouden van het schip.'),

(101, 3, 'theorie', CURRENT_TIMESTAMP(), 'IMPORT','Het kennen van twee andere reefsystemen dan die op het ‘eigen’ schip', 
'Theoretische kennis over het werken met het reefsysteem van het eigen schip, De kandidaat moet kunnen aangeven waar de belangrijkste foutoorzaken liggen.
Twee andere reefsystemen kennen dan die van het ‘eigen’ schip.')
;
COMMIT;
