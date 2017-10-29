package retodardos

import java.text.Normalizer

import org.springframework.stereotype.Service

@Service
class FilterService {

  private static final SIMPLE_WORDS = [
     'DINERO', 'BANCO', 'TARJETA', 'TENGO', 'PAGAR', 'CREDITO', 'DEUDOR', 'ADINERADO',
  ]
  private static final SPECIFIC_MOVEMENT = [
    /NO\sTENGO\sDINERO/, /TENGO\sQUE\sPAGAR\sLA\sTARJETA/, /NECESITO\sUN\sCREDITO\sFACIL/, /LE\sDEBO\sAL\sBANCO/
  ]

  /*
  'NO TENGO DINERO', 'TENGO QUE PAGAR LA TARJETA', 'NECESITO UN CREDITO FACIL, 'LE DEBO AL BANCO' 
  */

  String clean( String text ) throws Exception {
    def output = Normalizer.normalize( text, Normalizer.Form.NFD ).replaceAll(
        /[\p{InCombiningDiacriticalMarks}]/, '' ).toUpperCase()
    
    SPECIFIC_MOVEMENT.each{
      if (output =~ it){ 
          output = it
          output = output.replaceAll(/\\s/,' ')
      }    
    }
    
    SIMPLE_WORDS.each {
      if (output =~ it){ 
          output = "<h3> Keyword identificado </h3>"
      //output = output.replaceAll( /\s${it}\s/, ' ' )
      }
    }
    output = output.replaceAll(/RT\s@/,'').trim()  
    output

  }

}

