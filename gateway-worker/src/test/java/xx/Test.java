package xx;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Test {
    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>();
        StringBuilder stringBuilder = new StringBuilder(100);
        int i = 0;
//        Thread.sleep(60 * 1000);
        while (true){
//            stringBuilder.setLength(0);
            stringBuilder.delete(0,stringBuilder.length());
//            stringBuilder.trimToSize();
//            stringBuilder.setLength(100);
            i++;
            if(i>1000000){
                break;
            }
            stringBuilder.append("key:");
            stringBuilder.append(System.currentTimeMillis());
            stringBuilder.append(i);
            map.put(stringBuilder.toString(),new Object());
        }
        System.out.println("===========");
        Thread.sleep(30 * 1000);
        map.clear();
        Thread.sleep(3600 * 1000);
    }
}
