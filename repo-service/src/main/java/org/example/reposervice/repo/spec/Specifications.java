package org.example.reposervice.repo.spec;


//import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.http.ContentType;
import org.example.reposervice.repo.config.Config;


public class Specifications {

    private static Specifications spec;

    // if a method is written from Upper-case letter - it`s a constructor
    private Specifications() {
    }

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();

        }
        return spec;
    }

  /*  private RequestSpecBuilder reqBuilder() {

        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("http://" + Config.getProperties("host"));
        requestBuilder.setContentType(ContentType.JAR); //or should I use application/java-archive?
        requestBuilder.setAccept(ContentType.JAR);
        return requestBuilder;
    }
        /*

    /*
    public RequestSpecification authSpec(User user) {
        var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri("http://" + user.getUsername() + ":" + user.getPassword() + "@" + Config.getProperties("host"));
        return requestBuilder.build();
    }


    public RequestSpecification unauthSpec() {
        var requestBuilder = reqBuilder();
        return requestBuilder.build();
    }

 */
}

