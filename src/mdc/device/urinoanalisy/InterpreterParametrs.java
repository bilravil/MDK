package mdc.device.urinoanalisy;

import com.uroweb.device.bc01.BC01Data;
import java.io.Console;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 *
 * @author Aynur
 */

public class InterpreterParametrs {
    
    public String  interpreter(String typeUnit, ArrayList<BC01Data> dataList){
        switch (typeUnit) {
            case "traditional": return traditioanalUnit(dataList);
               
            case "international": return internationalUnit(dataList);
                
            case "default": return defaultUnit(dataList);
                
            default:
                throw new AssertionError();
        }
    }
    
    public String traditioanalUnit(ArrayList<BC01Data> dataList){
        Console out = System.console();
        String deviceFieldView = "";
        String result = "";
        for (BC01Data item : dataList) {
            for (Field f : item.getClass().getDeclaredFields()) {
                if (f.getModifiers() != Modifier.PUBLIC) {
                    continue;
                }
                String fieldName = f.getName();
                try{
                    switch (fieldName){
        //<editor-fold defaultstate="collapsed" desc="comment">


                        case "uro": 
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0.2mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "2mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "4mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "8mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "bld":switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "0.03mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "0.08mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "0.15mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "0.75mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "bil": 
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "1mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "3mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "6mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "ket":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "15mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "40mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "80mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "glu":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "50mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "100mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "250mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "500mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 5:
                                    deviceFieldView = "1000mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "pro":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "15mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "30mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "100mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "300mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "ph": 
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "5";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "6";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "7";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "8";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "9";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "nit":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "0.12mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "leu":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "15/ul";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "70/ul";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "125/ul";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "500/ul";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "sg":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "1.005";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "1.010";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "1.015";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "1.020";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "1.025";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 5:
                                    deviceFieldView = "1.030";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "vc":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "10mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "25mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "50mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "100mg/dl";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        default:
        //</editor-fold>
                    }                
                } catch (IllegalAccessException | NullPointerException e) {
                    System.out.println(e);
                }
            }           
        }return result;
    }
    
    public String defaultUnit(ArrayList<BC01Data> dataList){
        Console out = System.console();
        String deviceFieldView = "";
        String result = "";
        for (BC01Data item : dataList) {
            for (Field f : item.getClass().getDeclaredFields()) {
                if (f.getModifiers() != Modifier.PUBLIC) {
                    continue;
                }
                String fieldName = f.getName();
                //out.printf(fieldName + "/n");
                try{
                switch (fieldName){
                    case "uro": 
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "Norm";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "2+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = ">=3+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "bld":switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "+-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "2+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 4:
                                deviceFieldView = "3+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "bil": 
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "2+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "3+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "ket":
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "2+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "3+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "glu":
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "+-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "2+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 4:
                                deviceFieldView = "3+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 5:
                                deviceFieldView = "4+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "pro":
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "+-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "2+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 4:
                                deviceFieldView = ">=3+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "ph": 
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "5";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "6";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "7";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "8";
                                result = result + deviceFieldView + ";";
                                break;
                            case 4:
                                deviceFieldView = "9";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "nit":
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "leu":
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "+-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "2+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 4:
                                deviceFieldView = "3+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "sg":
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "<=1.005";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "1.010";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "1.015";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "1.020";
                                result = result + deviceFieldView + ";";
                                break;
                            case 4:
                                deviceFieldView = "1.025";
                                result = result + deviceFieldView + ";";
                                break;
                            case 5:
                                deviceFieldView = ">=1.030";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    case "vc":
                        switch(Integer.parseInt(f.get(item).toString())){
                            case 0:
                                deviceFieldView = "-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 1:
                                deviceFieldView = "+-";
                                result = result + deviceFieldView + ";";
                                break;
                            case 2:
                                deviceFieldView = "1+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 3:
                                deviceFieldView = "2+";
                                result = result + deviceFieldView + ";";
                                break;
                            case 4:
                                deviceFieldView = "3+";
                                result = result + deviceFieldView + ";";
                                break;
                            default:
                                throw new AssertionError();
                        }
                        break;
                    default:
                        //System.out.println("sdds "+fieldName);
                                break;
                }
                
                } catch (IllegalAccessException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
           // out.printf("%s", result+"\n");
        }return result;
    }
    
    public String internationalUnit(ArrayList<BC01Data> dataList){
        String deviceFieldView = "";
        String result = "";
        
        for (BC01Data item : dataList) {
            for (Field f : item.getClass().getDeclaredFields()) {
                if (f.getModifiers() != Modifier.PUBLIC) {
                    continue;
                }
                String fieldName = f.getName();
               // System.out.println(fieldName + "/n");
                try{
                    switch (fieldName){
                        //<editor-fold defaultstate="collapsed" desc="comment">
                        case "uro": 
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "3.3umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "33umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "66umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "131umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "bld":switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "-";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "10/ul";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "25/ul";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "50/ul";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "250/ul";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "bil": 
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "17umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "50umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "100umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "ket":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "1.5mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "4.0mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "8.0mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "glu":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "2.8mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "5.5mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "14mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "28mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 5:
                                    deviceFieldView = "55mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "pro":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0g/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "0.15g/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "0.3g/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "1g/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "3g/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "ph": 
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "5";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "6";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "7";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "8";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "9";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "nit":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "-";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "18umol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "leu":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "-";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "-";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "-";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "-";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "-";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "sg":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "<=1.005";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "1.010";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "1.015";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "1.020";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "1.025";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 5:
                                    deviceFieldView = ">=1.030";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        case "vc":
                            switch(Integer.parseInt(f.get(item).toString())){
                                case 0:
                                    deviceFieldView = "0mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 1:
                                    deviceFieldView = "0.6mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 2:
                                    deviceFieldView = "1.4mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 3:
                                    deviceFieldView = "2.8mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                case 4:
                                    deviceFieldView = "5.6mmol/l";
                                    result = result + deviceFieldView + ";";
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                            break;
                        default: 
                            //System.out.println("sdds "+fieldName);

                    //</editor-fold>
                    }
                } catch (IllegalAccessException | NullPointerException e) {
                    System.out.println(e);
                }  
            }            
        }return result; 
    }
}

