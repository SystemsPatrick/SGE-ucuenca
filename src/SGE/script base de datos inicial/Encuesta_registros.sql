
INSERT INTO USUARIO VALUES (1717226201,'Jhon','Av. Loja y 10 de Agosto','2475034','patricio.fajardo96@ucuenca.edu.ec');
INSERT INTO USUARIO VALUES (1234,'Maribel','Totoracocha','2475034','patricio.fajardo96@ucuenca.edu.ec');

INSERT INTO TIPO_ENCUESTA VALUES (1,'Marketing');
INSERT INTO TIPO_ENCUESTA VALUES (2,'Seguridad de Datos');

INSERT INTO ENCUESTA VALUES (1,'Seguridad de la Informacion','En la encuesta consta preguntas principales de seguridad informatica',2);

INSERT INTO PREGUNTA VALUES (1,1,'Como se llama la empresa a la cual pertenece?');
INSERT INTO PREGUNTA VALUES (1,2,'Que version de Windows esta instalada en el equipo que normalemente usas para conectarte a Internet?');
INSERT INTO PREGUNTA VALUES (1,3,'Que navegador usa comunmente?');

INSERT INTO P_OPMULTIPLE VALUES (1,2,1,'Windows 10');
INSERT INTO P_OPMULTIPLE VALUES (1,2,2,'Windows 8');
INSERT INTO P_OPMULTIPLE VALUES (1,2,3,'Windows 7');
INSERT INTO P_OPMULTIPLE VALUES (1,2,4,'Windows Vista');
INSERT INTO P_OPMULTIPLE VALUES (1,2,5,'Windows XP');
INSERT INTO P_OPMULTIPLE VALUES (1,2,6,'Otro');

INSERT INTO P_OPMULTIPLE VALUES (1,3,1,'Mozilla');
INSERT INTO P_OPMULTIPLE VALUES (1,3,2,'Google Chrome');
INSERT INTO P_OPMULTIPLE VALUES (1,3,3,'Opera');
INSERT INTO P_OPMULTIPLE VALUES (1,3,4,'Otro');