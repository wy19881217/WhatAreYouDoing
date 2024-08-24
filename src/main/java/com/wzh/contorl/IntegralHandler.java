package com.wzh.contorl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzh.entity.Integralevent;
import com.wzh.mybatis.IntegraleventMapper;
import com.wzh.service.HttpService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IntegralHandler {
    public final static String APPID = "wxeb80158d89b56d45";

    public final static String SECRET = "49eaec1d8783c81530d1998e0d899dec";
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    IntegraleventMapper integraleventMapper;

    @Autowired
    ObjectMapper jacksonObjectMapper;

    String token;
    DateTime tokenExpiresTime = DateTime.now();

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping("/addIntegralEvent")
    public Map<String, Object> addIntegralEvent(@RequestBody Map<String, Object> body) {
        Integralevent integralevent = new Integralevent();
        integralevent.setProtagonistId((Integer) body.get("protagonistId"));
        integralevent.setEventInfo((String) body.get("eventInfo"));
        integralevent.setIntegralVariation((Integer) body.get("integralVariation"));
        integraleventMapper.insert(integralevent);
        Map<String, Object> result = new HashMap();
        result.put("code", 200);
        result.put("msg", "添加成功！");
        return result;
    }

    public boolean checkToken() {
        boolean result = true;
        if (token == null || tokenExpiresTime.isBeforeNow()) {
            try {
                String response = HttpService.httpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appId=" + APPID + "&secret=" + SECRET);
                Map<String, Object> map = jacksonObjectMapper.readValue(response, Map.class);
                if (map.get("access_token") != null && map.get("expires_in") != null) {
                    token = (String) map.get("access_token");
                    tokenExpiresTime = DateTime.now().plusSeconds((int) map.get("expires_in"));
                } else {
                    result = false;
                }
            } catch (IOException e) {
                result = false;
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @RequestMapping("/getPhoneNumber")
    @ResponseBody
    public Map<String, Object> getPhoneNumber(@RequestBody Map<String, Object> body) throws IOException {
        String code = (String) body.get("code");
        if (checkToken()&&code!=null) {
            Map<String, String> param = new HashMap<>();
            param.put("code", code);
            String json =jacksonObjectMapper.writeValueAsString(param);
            String response = HttpService.httpPost("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + token, new HashMap<>(), json);
            System.out.println(response);
        }
        Map<String, Object> result = new HashMap();
        result.put("code", 200);
        result.put("msg", "添加成功！");
        return result;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile image,String data) throws  IOException {
        String filename=DateTimeFormat.forPattern("yyyyMMddHHmmss").print(DateTime.now())+".jpg";
        image.transferTo(new File(httpServletRequest.getServletContext().getRealPath("/")+ filename));
        Map<String, Object> result = new HashMap();
        result.put("code", 200);
        result.put("msg", "添加成功！");
        return result;
    }
}
