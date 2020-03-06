# authServerSample

jks 생성

keytool -genkeypair -alias Key의 별칭
                    -keyalg RSA(key암호화 알고리즘)
                    -keypass Key 암호
                    -keystore 저장될 key의 파일명 
                    -storepass 저장될 파일의 암호
                    
#key 깨질경우    
keytool -genkeypair -alias samplejks -keyalg RSA -validity 3650 -keypass samplejkspwd -keystore sampleauth.jks -storepass storepwd

# 공개키 정보 확인
keytool -list -rfc --keystore sampleauth.jks | openssl x509 -inform pem -pubkey