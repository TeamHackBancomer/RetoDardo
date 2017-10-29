package retodardos

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(view:"/index")
        //"/"(controller:"twitterLogin", action: "index")
        "/callback"(controller:"twitterLogin", action: "callback")
        //"/callback"(redirect(url: "http://localhost:8080/"))
        //"/callback"(view:"/index")
        "/finish"(controller:"twitterLogin", action: "finish")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
