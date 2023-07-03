from Crypto.Cipher import PKCS1_OAEP
from Crypto.PublicKey import RSA
from cryptography.fernet import Fernet
from base64 import b64decode

def delete_text(decryptedKeyRSA):
    decryptedKeyRSA = decryptedKeyRSA.replace('-----BEGIN RSA PRIVATE KEY-----', '')
    decryptedKeyRSA = decryptedKeyRSA.replace('-----END RSA PRIVATE KEY-----', '')
    return decryptedKeyRSA

def decryption_complete(keyStringFernet, keyStringRSA, messageEncrypted):
    messageDecryptedFernet = Fernet(key=keyStringFernet.encode()).decrypt(messageEncrypted.encode())
    decryptedKeyRSA = Fernet(key=keyStringFernet.encode()).decrypt(keyStringRSA.encode())
    decryptedKeyRSA = decryptedKeyRSA.decode("utf-8")

    decryptedKeyRSA = delete_text(decryptedKeyRSA=decryptedKeyRSA)

    keyBase64 = b64decode(decryptedKeyRSA)
    private_key = RSA.importKey(keyBase64)

    cipher = PKCS1_OAEP.new(key=private_key)
    cipher_text_decrypted = cipher.decrypt(messageDecryptedFernet)
    return cipher_text_decrypted