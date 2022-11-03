    X X X X
    | | | |
    | | | --- 1 -> Islem Kodu
    | | ----- 2 -> Islem Kodu
    | ------- 3 -> 2. Success
                -> 4. Error
                -> 5. Server Error
    --------- 4 -> 1. Auth Service
                   2. Main Service
                   3. User Service
# AUTH SERVICES
## 12XX: Basarili Islemler
    1200: Kayýt Basarili
    1201: Giris Basarili
## 14XX: Hatali Islemler
    1400: Kayýt Basarisiz
    1401: Giris Basarisiz
    1402: Kullanici Adi Veya Sifre Hatali
    1403: Kullanici Zaten Kayitli
## 15XX: Sunucu Hatalari
    1500: Kayýt Sunucu Hatasi
    1501: Giris Sunucu Hatasi


# MAIN SERVICES
## 2000: AUTHENTICATION

# USER SERVICES
## 3000: AUTHENTICATION