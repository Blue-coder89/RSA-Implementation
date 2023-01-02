import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Random;
import java.util.stream.IntStream;



public class User{
    public ArrayList<ArrayList<BigInteger>> messages; // List of messages received by the user
    public BigInteger[] publicKey; // public key
    private BigInteger[] privateKey; // private key
    User(){
       this.messages = new ArrayList<ArrayList<BigInteger>>(); 
       this.publicKey = new BigInteger[2];
       this.privateKey = new BigInteger[2];
       RSA(); 
    }  
    private BigInteger exp(BigInteger a,BigInteger b,BigInteger mod) // binary exponentation function
        {
            BigInteger ans = BigInteger.ONE;
            while(b.compareTo(BigInteger.ZERO) > 0)
                {
                    if(b.mod(BigInteger.TWO).compareTo(BigInteger.ONE) == 0) 
                       ans = ans.multiply(a).mod(mod);
                    a = a.multiply(a).mod(mod);
                    b = b.divide(BigInteger.TWO);
                }
            return ans;
        }

    private void RSA(){ 
        // This function generates public and private key
        Random rand = new Random();
        BigInteger p = BigInteger.probablePrime(256,rand);
        BigInteger q = BigInteger.probablePrime(256,rand);
        BigInteger n = p.multiply(q);
        BigInteger m = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.probablePrime(128,rand);
        while(m.gcd(e).compareTo(BigInteger.ONE) > 0 && m.subtract(e).compareTo(BigInteger.ONE) > 0)
            {
                e = e.add(BigInteger.ONE);
            }
        BigInteger d = e.modInverse(m);
        this.publicKey[0] = e;
        this.publicKey[1] = n;
        this.privateKey[0] = d;
        this.privateKey[1] = n;
    }

    private ArrayList<BigInteger> encrypt(String message,BigInteger []publicKey)
        {
            // encryption function
            IntStream messageStream = message.chars();
            ArrayList <BigInteger> cipherText = new ArrayList<BigInteger>();
            messageStream.forEach(a -> {
               cipherText.add(exp(BigInteger.valueOf(a),publicKey[0],publicKey[1])); 
            });
            return cipherText;
        }
    private String decrypt(ArrayList<BigInteger>cipherText)
        {
            // decryption function
            ArrayList<Character> messageArray = new ArrayList<Character>();
            cipherText.forEach(a -> {
                BigInteger decryptedData = exp(a,this.privateKey[0],this.privateKey[1]);
                messageArray.add((char)decryptedData.intValue());
            });
            String message = "";
            for(char ch:messageArray)
                message += ch;
            return message;
        }
    
    public void Send(String message,User receiver)
        {
            // This function can be modified to send message to another user
            ArrayList<BigInteger>cipherText = this.encrypt(message,receiver.publicKey);
            // send the cipher text through networking
            receiver.Receive(cipherText);
        }
    
    private void Receive(ArrayList<BigInteger> cipherText)
        {
            // This receives the message and adds it to the message list
            this.messages.add(cipherText);
        }   
    
    public void view(ArrayList<BigInteger> cipherText)
        {
            // To print any message 
            System.out.println(this.decrypt(cipherText));
        }
    
}
