package retodardos

//import grails.gorm.transactions.Transactional

//@Transactional

import org.codehaus.groovy.grails.commons.*
import twitter4j.*
import twitter4j.auth.*
import static grails.async.Promises.*
import static java.util.concurrent.TimeUnit.*
import grails.async.*


class TwitterService {

    static transactional = false
    static scope = 'session'

def consumerKey="nb3RJx6XsQlSFGvvbt6F3JswW"
def consumerSecret="A8cg5aPEmGkuhperljYmFH8LIgW7NTuiZdCxRFGtxUmYhlpOOQ"


    Twitter twitter
    RequestToken requestToken
    FilterService filterService

    String authenticate(String returnUrl) {
        twitter = new TwitterFactory().getInstance()
        twitter.setOAuthConsumer(consumerKey, consumerSecret)
        requestToken = twitter.getOAuthRequestToken(returnUrl)
        return requestToken.getAuthenticationURL()
    }

    User verifyCredentials(String oauth_verifier) {
        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier)
        return twitter.verifyCredentials()
    }

    String getTimeline() {
        String x=""
        def keys=[];
        keys.add("como cuando le debes mas al banco que lo que tienes de quincena")
        keys.add("jajaja que buena fiesta")
        keys.add("chido el reve")
        keys.add("alguien sabe como salir del buro de credito?")
        keys.add("mi credito me duro una semana y debia ser un mes")
        keys.add("hoy a donde?")
        keys.add("hablen con los del banco y diganles que no era mi intencion sobre girar mi tarjeta")
        keys.add("no que eran meses sin interese?")
        keys.add("a ver como salgo de la semana ")
        try {

            List<Status> statuses = twitter.getHomeTimeline();
            statuses.each{
                //x=x+"${it.text} <br><br>"
                keys.add(it.text)
                println "Timeline:    ${it.text} \n"
            }
            
            //println("done.");
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get messages: " + te.getMessage());
            
        }
        
        keys.each{
            it= filterService.clean(it)
            x=x+"${it} <br><br>"
        }

        x
    }
    def serviceMethod() {

    }
}