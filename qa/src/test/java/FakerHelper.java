import com.github.javafaker.Faker;

public class FakerHelper {
    static Faker faker = new Faker();

    public static String randomFirstName(){
        return faker.name().firstName();
    }

    public static String randomLastName(){
        return faker.name().lastName();
    }
}