
public class test{
   
    public static void main(String args[])
    {
        User u1 = new User();
        User u2 = new User();
        // System.out.println(u1.publicKey[0].toString() + " " + u1.publicKey[1].toString());
        // System.out.println(u2.publicKey[0].toString() + " " + u2.publicKey[1].toString());
        u1.Send("Hi ! How are You?",u2);
        u2.view(u2.messages.get(0));
        u2.Send("I am fine :) Thank You !",u1);
        u1.view(u1.messages.get(0));
    }
}