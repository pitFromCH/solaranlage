package com.pit.solarserver.model;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

public class SolarSwitch {

    private static Logger logger = LoggerFactory.getLogger(SolarSwitch.class);

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String switchOnString = "on";
    private static final String switchOffString = "off";
    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalOutput ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);

    public void setSwitch(String url, boolean switchOn, int boilertemp, int hourHeating, int hourHeating48) {
        /** create gpio controller */
        //https://pi4j.com/1.2/pins/model-3b-rev1.html

        RestTemplate restTemplate = new RestTemplate();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        if (switchOn) {
            //set led
            //ledPin.high();
            //write switch on to url
            String html = restTemplate.getForObject(url, String.class, switchOnString);

            //inform via mail
            mailMessage.setTo("p-andres@gmx.ch");
            mailMessage.setSubject("Solar-App-Info");
            mailMessage.setText("Please switch the water heater to ON \n " +
                                "Boilertemperatur = " + boilertemp + "\n" +
                                "Heizdauer nächster Tag =" + hourHeating + "\n" +
                                "Heizdauer nächsten 48h = " + hourHeating48);
            mailMessage.setFrom("p-andres@gmx.ch");
            logger.info("Mail send ");
            javaMailSender.send(mailMessage);
        } else {
            //set led
            //ledPin.low();
            //write switch off to url
            String html = restTemplate.getForObject(url, String.class, switchOffString);
            mailMessage.setTo("p-andres@gmx.ch");
            mailMessage.setSubject("Solar-App-Info");
            mailMessage.setText("Please switch the water heater to OFF");
            mailMessage.setFrom("p-andres@gmx.ch");
        }
        logger.info("Mail sending ");
        javaMailSender.send(mailMessage);
    }

}
