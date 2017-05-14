/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdc.gui.tools;

import java.security.SecureRandom;

/**
 *
 * @author Равиль
 */
public class Translate {
static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
static SecureRandom rnd = new SecureRandom();

private String alpha = new String("абвгдеёжзиыйклмнопрстуфхцчшщьэюя");
private String[] _alpha = {
"Курите ли вы?(курение одной или более сигарет в день)",
"Рост",
"Окружность талии",
"Вес",
"Экг",
"Патологии выявлены?",
"Глюкоза (моча), ммоль/л",
"Протеин, г/л",
"Кетоны, ммоль/л",
"Эритроциты (моча),кол-во/мкл",
"Лейкоциты (моча), кол-во/мкл",
"Удельная плотность",
"pH",
"Уробилиноген, мкмоль/л",
"Аскорбиновая кислота, г/л",
"Нитриты",
"Билирубин (моча), мкмоль/л",
"Цвет",
"Мутность",
"Артериальное давление верхнее",
"Артериальное давление нижнее",
"Пульс",
"Правый",
"Левый"};
        
 private String[] betta = {
"smoke",
"height",
"waist",
"weight",
"ecg",
"pathology",
"glucose",
"protein",
"ketones",
"redblodd",
"whiteblood",
"gravity",
"pH",
"urob",
"ascorbic",
"nitrite",
"biliruben",
"color",
"turbidity",
"toppress",
"lowpress",
"pulse",
"right",
"left"};

        
        
        public String  translate(String txt){           
            for(int i =0; i < _alpha.length; i++){
            if(_alpha[i].equals(txt)){
                return betta[i];
            }
            }return "";
        }
        
        public String  unTranslate(String txt){           
            for(int i =0; i < betta.length; i++){
            if(betta[i].equals(txt)){
                return _alpha[i];
            }
            }return "";
        }
        
}
