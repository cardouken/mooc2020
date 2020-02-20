
public class YourFirstBankTransfer {

    public static void main(String[] args) {
        Account matthew = new Account("Matthews account", 1000.0);
        Account myAccount = new Account("My account", 0);

        matthew.withdrawal(100);
        myAccount.deposit(100);

        System.out.println(matthew);
        System.out.println(myAccount);
    }
}
