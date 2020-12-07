package dw.fi;
//根据数据的type，判断是什么类型的数据。将数据类型放入header，方便拦截器分流数据。

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }
//单个时间拦截
    @Override
    public Event intercept(Event event) {
        String eventBody = new String(event.getBody());
//        JSONObject json = JSONObject.parseObject(eventBody);
//        String type = (String) json.get("type");
        boolean b = Pattern.compile(".*?\"type\":\"startup\".*?").matcher(eventBody).find();
        if (b){
            event.getHeaders().put("type","startup");
        }else{
            event.getHeaders().put("type","event");
        }

        return event;
    }
//批量事件拦截
    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event : list) {
            intercept(event);
        }

        return list;
    }

    @Override
    public void close() {

    }

    public  static  class  Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new MyInterceptor();
        }

        @Override
        public void configure(Context context) {

        }


        public static void main(String[] args) {
            String data="{\"area\":\"heilongjiang\",\"uid\":\"23097\",\"os\":\"ios\",\"ch\":\"appstore\",\"appid\":\"data2020\",\"mid\":\"mid_20766\",\"type\":\"startup\",\"vs\":\"1.2.0\"}";
            Matcher matcher = Pattern.compile(".*?\"type\":\"startup\".*?").matcher(data);
            if (matcher.find()){
                String group = matcher.group(0);
                String[] split = group.split(",");
                String s = split[split.length - 1];
                System.out.println(group);
            }
        }
    }

}
