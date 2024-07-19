//NaoyaIida

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class e2_01_9_7 {

    private static String loadJson(String path) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //テキストファイルを読み込んで文字列で返してるだけ
        String json = loadJson("json.txt");

        //読み込んだJSON文字列をObjectMapperを使ってUserProfileクラスのインスタンスにデシリアライズする。
        //UserProfileクラスはJSONデータの構造を表すクラスで、適切なフィールド、ゲッター、セッターを持っている必要がある。
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserProfile userProfile = mapper.readValue(json, UserProfile.class);

            //解析した結果を利用
            System.out.println("Name: " + userProfile.getName());
            System.out.println("Age: " + userProfile.getAge());
            System.out.println("Preferences: " + userProfile.getPreferences());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class UserProfile {
    private String name;
    private int age;
    private List<String> preferences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
}
/*
Name: John Doe
Age: 30
Preferences: [Music, Books, Travel]
 */
