package org.example.client.request;


import org.example.client.request.specificRequests.Get;
import org.example.client.request.specificRequests.Post;
import org.example.client.request.specificRequests.UnknownType;

public class RequestFactoryImpl implements  RequestFactory {
    private String data ;
    private String method;

    public  RequestFactoryImpl(String data)  {
        this.data=data;
        this.method = data.split(" ")[0];
    }
    public synchronized Request initialize() {

        //якщо запит був зроблений за допомогою методу GET
        if (method.equals("GET"))
        {
           return new Get(data);
        }
        //якщо запит був зроблений за допомогою методу POST
        if (method.equals("POST"))
        {
            return new Post(data);
        }
        //якщо запит був зроблений за допомогою методу,
        // оброблення якого не описане (методу,відмінного від GET та POST)
        else return new UnknownType(data);
    }
}
