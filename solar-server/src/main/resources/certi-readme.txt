- ZeroSSL <noreply@zerossl.com> Zertifikat erstellen
- keystore erstellen

- Importieren des Zertifikate solaranlage.andreses.ch in den keystore
- private key umwandeln in ein P12-File
- private.key und certificate.crt zum raspberry kopieren

openssl pkcs12 -export -in certificate.crt -inkey private.key -name "solaranlage.andreses.ch" -out solaranlage.p12
C:\temp\solaranlage.andreses.ch>scp pi@192.168.1.172:/tmp/solaranlage.p12 c:/temp/solaranlage.andreses.ch

- importieren des P12 files in den keystore
- Keystore ersetzen
