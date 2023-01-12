/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.tokobuku;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author raiha
 */
@RestController
@CrossOrigin
public class TokoController {
    
    Barang data = new Barang();
    BarangJpaController control = new BarangJpaController();
    
    @GetMapping(value = "/GET", produces = APPLICATION_JSON_VALUE)
    public List<Barang> getData(){
        List<Barang> buffer = new ArrayList<>();
        buffer = control.findBarangEntities();
        
        return buffer;
    }
    
    @PostMapping(value = "/POST", consumes = APPLICATION_JSON_VALUE)
    public String sendData(HttpEntity<String> dataSend) throws JsonProcessingException {
        
        String feedback = "Do Nothing";
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(dataSend.getBody(), Barang.class);
        
       try{
           control.create(data);
           feedback = data.getNamaBuku() + " Saved";
       }catch (Exception Error) {
           feedback = Error.getMessage();
       }
        return feedback;
    }
    
    @PutMapping(value = "/PUT", consumes = APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> datasend) throws JsonProcessingException{
        
        String feedback = "Do Noting";
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(datasend.getBody(), Barang.class);
        try{
            control.edit(data);
            feedback = data.getNamaBuku() + " Edited";
        } catch (Exception Error) {
            feedback = Error.getMessage();
        }
        return feedback;
    }
    
    @DeleteMapping(value = "/DELETE", consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> dataSend) throws JsonProcessingException {
        
        String feedback = " Do Nothing";
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(dataSend.getBody(), Barang.class);
        try{
            control.destroy(data.getId());
            feedback = "Data has been Deleted";
        } catch (Exception Error) {
            feedback = Error.getMessage();
        }
        return feedback;
    }
    
    
}
