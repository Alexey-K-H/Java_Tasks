package GameModel;

//Содержит информацию для таблицы рекордов

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class High_scores {
    private final ArrayList<String> scores;
    private final String file = "High_Scores.txt";
    public final static int MAX_LINES = 5;//Максимальное число строк в таблице

    public High_scores(){
        scores = new ArrayList<String>();
        //Этот список должен соритроваться по количеству очков, поэтому нужен comparator
        Comparator<String> comparator = new Comparator<String>() {
            public int compare(String o1, String o2) {
                //Необходимо разделить строку на части, чтобы отделить значение очков игрока
                StringTokenizer stringTokenizer = new StringTokenizer(o1);
                String name_o1 = stringTokenizer.nextToken();//Берем имя
                int value_o1 = Integer.parseInt(stringTokenizer.nextToken());//Берем строку с числом очков и преобразуем в число

                //Теперь берем информацию второго игрока
                stringTokenizer = new StringTokenizer(o2);
                String name_o2 = stringTokenizer.nextToken();
                int value_o2 = Integer.parseInt(stringTokenizer.nextToken());

                //Если их рекорды совпадают то сортируем их имена
                if(value_o1 == value_o2){
                    return name_o1.compareTo(name_o2);
                }
                else if(value_o1 > value_o2){
                    return -1;
                }
                else {
                    return 1;
                }
            }
        };
        //Теперь берем данные из файла и помещаем в наш список
        //Затем сортируем их с поммощью comparator-а
        BufferedReader bufferedReader = null;
        try
        {
            bufferedReader = new BufferedReader(new FileReader(file));
            for(int i = 0; i < MAX_LINES; i++)
            {
                String line = bufferedReader.readLine();
                if(null == line || "\n".equals(line) || line.isEmpty()){
                    break;
                }
                scores.add(line);
            }
            Collections.sort(scores, comparator);
        } catch (IOException ex){
            System.exit(0);
        }
        finally {
            try {
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }
            catch (IOException ex){
                System.exit(0);
            }
        }
    }

    //После каждой игры нужно записывать обновленный список рекордов
    public void writeFile(){
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String score : scores) {
                printWriter.write(score);
            }
        } catch (IOException ex) {
            System.exit(0);
        }
    }

    //Проверка того, что установлен новый рекорд
    public boolean isSetNewRecord(int score){
        for(String curr_score : scores){
            //Для каждого рекорда из списка берем значение рекорда и сравниваем с текущим
            StringTokenizer stringTokenizer = new StringTokenizer(curr_score);
            stringTokenizer.nextToken();
            int value = Integer.parseInt(stringTokenizer.nextToken());
            if(score > value){
                return true;
            }
        }
        //Но добавить рекорд мы можем если размер таблицы позволит
        return scores.size() < MAX_LINES;
    }

    //Добавление нового рекорда в список
    public void setRecord(int score, String name){
        if(!"".equals(name)){
            boolean is_this_place = false;//В нужном ли мы месте для записи рекорда в таблице
            for(int i = 0; i < scores.size(); i++){
                StringTokenizer stringTokenizer = new StringTokenizer(scores.get(i));
                stringTokenizer.nextToken();
                int value = Integer.parseInt(stringTokenizer.nextToken());
                if(score > value)
                {
                    scores.add(i, name + " " + score);
                    is_this_place = true;
                    if(scores.size() > MAX_LINES)
                    {
                        scores.remove(MAX_LINES);//Очищаем последний
                    }
                    break;
                }
            }
            //Если же таблица не заполнена до конца
            if(scores.size() < MAX_LINES && !is_this_place){
                scores.add(name + " " + score);
            }
            writeFile();
        }
    }

    public void resetScores()
    {
        scores.clear();
        writeFile();
    }

    public int getScoresSize(){
        return scores.size();
    }

    public String getScore(int ind){
        return scores.get(ind);
    }
}
