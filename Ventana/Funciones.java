/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventana;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author aaron
 */
public class Funciones {
    
    private ActionListener al;
    private Timer t;
    
    public void CalcularPosicion(final JFrame a[], int ven){
        int total = 0;
        for (int i = 0; i < a.length; i++) {
            total = total + a[i].getSize().width;
        }
        int startPoint = ((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-(total/2);
        if (ven == 1) {
            for (int i = 0; i < a.length; i++) {
                try {
                    a[i].setLocation(startPoint+a[i-1].getSize().width, 
                        ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(a[i].getSize().height/2));
                } catch (Exception e) {            
                    Desplazar(a, startPoint, ven);
                }
            }
        } else if (ven == 2) {
            Desplazar(a, startPoint, ven, 0);
        }
    }
    
    public void Desplazar(final JFrame b[], final int puntoInicio, final int ven){
        al =new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //for (int i = 1; i <= b.length; i++) {
                    if (b[0].getLocation().x > puntoInicio) {
                        try {
                            b[0].setLocation(b[0].getLocation().x-20, 
                                    ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(b[0].getSize().height/2));
                        } catch (Exception c) {
                            b[0].setLocation(puntoInicio, 
                                    ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(b[0].getSize().height/2));
                        }
                    } else{
                        t.stop();
                        Aparecer(b[ven]);
                    }
                }
            //}
        };
        t=new Timer(1,al);
        t.start();
    }
    public void Desplazar(final JFrame b[], final int puntoInicio, final int ven, int a){
        final int ancActual = b[0].getWidth() + b[1].getWidth();
        final int posActual = ((Toolkit.getDefaultToolkit().getScreenSize().width)/2)-(ancActual/2);
        al =new ActionListener() {
            int posActual2 = posActual;
            @Override
            public void actionPerformed(ActionEvent e) {
                //for (int i = 1; i <= b.length; i++) {
                    if (posActual2 > puntoInicio) {
                        //System.out.println(posActual +" "+puntoInicio );
                        try {
                            b[0].setLocation(b[0].getLocation().x-20, 
                                    ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(b[0].getSize().height/2));
                            b[1].setLocation(b[1].getLocation().x-20, 
                                    ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(b[1].getSize().height/2));
                            posActual2 = b[0].getLocation().x;
                        } catch (Exception c) {
                            b[0].setLocation(puntoInicio, 
                                    ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(b[0].getSize().height/2));
                            b[1].setLocation(puntoInicio+b[0].getSize().width, 
                                    ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(b[1].getSize().height/2));
                        }
                    } else{
                        t.stop();
                        b[2].setLocation(b[1].getLocation().x + b[1].getSize().width, 
                        ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(b[2].getSize().height/2));
                        Aparecer(b[ven]);
                    }
                }
            //}
        };
        t=new Timer(1,al);
        t.start();
    }
    
    public void Desplazar(final JFrame menu){
        al =new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (menu.getLocation().x < (Toolkit.getDefaultToolkit().getScreenSize().width/2)-(menu.getSize().width/2)) {
                    try {
                        menu.setLocation(menu.getLocation().x+20, 
                                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(menu.getSize().height/2));
                    } catch (Exception c) {
                        menu.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width/2)-(menu.getSize().width/2), 
                                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(menu.getSize().height/2));
                    }
                } else{
                    t.stop();
                }
            }
        };
        t=new Timer(1,al);
        t.start();
    }
    
    public void Desplazar(final JFrame ventanas[]){
        final int anchActual = ventanas[0].getSize().width + ventanas[1].getSize().width;
        final int posActual = ventanas[0].getLocation().x;
        al =new ActionListener() {
            int posActual2 = posActual;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (posActual2 < (Toolkit.getDefaultToolkit().getScreenSize().width/2)-(anchActual/2)) {
                    try {
                        ventanas[0].setLocation(ventanas[0].getLocation().x+20, 
                                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(ventanas[0].getSize().height/2));
                        ventanas[1].setLocation(ventanas[1].getLocation().x+20, 
                                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(ventanas[1].getSize().height/2));
                        posActual2 = ventanas[0].getLocation().x;
                    } catch (Exception c) {
                        ventanas[0].setLocation((Toolkit.getDefaultToolkit().getScreenSize().width/2)-(ventanas[0].getSize().width/2), 
                                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(ventanas[0].getSize().height/2));
                        ventanas[1].setLocation(ventanas[0].getLocation().x + ventanas[0].getSize().width, 
                                ((Toolkit.getDefaultToolkit().getScreenSize().height)/2)-(ventanas[1].getSize().height/2));
                    }
                } else{
                    t.stop();
                }
            }
        };
        t=new Timer(1,al);
        t.start();
    }
    
    public void Aparecer(final JFrame ventana){
        al =new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventana.getOpacity()<1) {
                    try {
                        ventana.setOpacity(ventana.getOpacity()+0.1f);
                    } catch (Exception c) {
                        ventana.setOpacity(1);
                    }
                    
                }else{
                t.stop();
                }
            }
        };
        t=new Timer(10,al);
        t.start();
    }
    
    public void Desaparecer(final JFrame ventana, final JFrame menu){
        al =new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventana.getOpacity()>0) {
                    try {
                        ventana.setOpacity(ventana.getOpacity()-0.1f);
                    } catch (Exception c) {
                        ventana.setOpacity(0);
                    }
                    
                }else{
                    t.stop();
                    ventana.dispose();
                    Desplazar(menu);
                }
            }
        };
        t=new Timer(10,al);
        t.start();
    }
    
    public void Desaparecer(final JFrame ventana, final JFrame menu[]){
        al =new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ventana.getOpacity()>0) {
                    try {
                        ventana.setOpacity(ventana.getOpacity()-0.1f);
                    } catch (Exception c) {
                        ventana.setOpacity(0);
                    }
                    
                }else{
                    t.stop();
                    ventana.dispose();
                    Desplazar(menu);
                }
            }
        };
        t=new Timer(10,al);
        t.start();
    }
    
    public void GetAllFrames(){
        int components = 2;
        System.out.println(components);
    }
}
