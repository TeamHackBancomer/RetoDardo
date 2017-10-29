package retodardos


import twitter4j.*

class TwitterLoginController {

    def twitterService

    def index = {}

    def login = {
        redirect url: twitterService.authenticate("http://localhost:8080/callback") // [1]
    }

    def callback () {
        def respuesta
        if (params.denied){
            flash.message = "Permiso denegado"

        } else {
            Usuario usuario = checkTwitterUser(twitterService.verifyCredentials(params.oauth_verifier))

            session.user = usuario
            println session.user

            respuesta=twitterService.getTimeline();
            
            //redirect action: index
        }
        
        render(text: """
            <html>
  <body>
  ${respuesta}
  </body>
</html>
            """, contentType: "text/html", encoding: "UTF-8")
        //this.finish()
        //redirect(url: "http://localhost:8080/finish" ,params: [text: 'Failed to update book'])
        //redirect action: index
    }



    private checkTwitterUser(User twitterUser) {
        Usuario user = Usuario.findByTwitterId(twitterUser.id)

        if (!user) {
            user = new Usuario(twitterId: twitterUser.id)
        }

        user.name = twitterUser.name
        user.screenName = twitterUser.screenName
        user.profileImg = twitterUser.profileImageURL.toString()

        user.save()
    }

    def logout = {
        session.invalidate()
        redirect action: index
    }
    /*
    def finish(){
        render(text: "<xml>${text2}</xml>", contentType: "text/xml", encoding: "UTF-8")
    }
    */
 }