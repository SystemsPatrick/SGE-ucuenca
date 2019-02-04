--eliminar el usuario si existe 

drop user "ENCUESTA_DB" cascade;

CREATE tablespace MyTableSpace datafile 'C:\oraclexe\app\oracle\oradata\XE\MyTableSpace.DBF' size 30M;

--CREAR UN USUARIO
create user ENCUESTA_DB identified by 12345
default tablespace MyTableSpace
quota 10M on MyTableSpace;
 
--CONECCION A LA BASE DE DATOS
grant connect, resource to ENCUESTA_DB;
connect ENCUESTA_DB/12345;
 
--CREACION DE TABLAS

--DROP TABLE RESPUESTA_TEXTO;
--DROP TABLE RESPUESTA_OPCION;
--DROP TABLE P_OPMULTIPLE;
--DROP TABLE PREGUNTA;
--DROP TABLE ENCUESTA;
--DROP TABLE TIPO_ENCUESTA;
--DROP TABLE USUARIO;

CREATE TABLE USUARIO
(
	cod_usuario number(10),
	nombre varchar2(30),
	direccion varchar2(50),
	telefono varchar2(10),
	correo varchar2(50),
	CONSTRAINT CP_USUARIO PRIMARY KEY (cod_usuario)
);
 
CREATE TABLE TIPO_ENCUESTA
(
	cod_tipo_encuesta number(5),
	nombre varchar2(30),
	CONSTRAINT CP_TIPO_ENCUESTA PRIMARY KEY (cod_tipo_encuesta)
);
 
CREATE TABLE ENCUESTA
(
	cod_encuesta number(5),
	nombre varchar2(30),
	descripcion varchar2(150),
	cod_tipo_encuesta number(5),
	CONSTRAINT CP_ENCUESTA PRIMARY KEY (cod_encuesta),
	CONSTRAINT CF_TIPO_ENCUESTA FOREIGN KEY (cod_tipo_encuesta) REFERENCES TIPO_ENCUESTA(cod_tipo_encuesta)
);
 
CREATE TABLE PREGUNTA
(
	cod_encuesta number(5),
	cod_pregunta number(5),
	descripcion varchar2(200),
	CONSTRAINT CP_ENCUESTA_PREGUNTA PRIMARY KEY (cod_encuesta, cod_pregunta),
	CONSTRAINT CF_ENCUESTA FOREIGN KEY (cod_encuesta) REFERENCES ENCUESTA(cod_encuesta)
);
 
CREATE TABLE P_OPMULTIPLE
(
	cod_encuesta number(5),
	cod_pregunta number(5),
	cod_opc_multiple number(5),
	desc_opcion varchar2(50),
	CONSTRAINT CP_E_P_OPMULTIPLE PRIMARY KEY (cod_encuesta,cod_pregunta,cod_opc_multiple),
	CONSTRAINT CF_E_P_OPMULTIPLE FOREIGN KEY (cod_encuesta, cod_pregunta) REFERENCES PREGUNTA(cod_encuesta, cod_pregunta)
);
 
CREATE TABLE RESPUESTA_TEXTO
(
	cod_encuesta number(5),
	cod_pregunta number(5),
	cod_usuario number(10),
	respuesta varchar2(50),
	CONSTRAINT CP_E_P_U_RESP_TEXTO PRIMARY KEY (cod_encuesta,cod_pregunta,cod_usuario),
	CONSTRAINT CF_E_P_RESP_TEXTO FOREIGN KEY (cod_encuesta, cod_pregunta) REFERENCES PREGUNTA(cod_encuesta, cod_pregunta),
	CONSTRAINT CF_US_RESP_TEXTO FOREIGN KEY (cod_usuario) REFERENCES USUARIO (cod_usuario)
);
 
CREATE TABLE RESPUESTA_OPCION
(
	cod_encuesta number(5),
	cod_pregunta number(5),
	cod_opc_multiple number(5),
	cod_usuario number(10),
	respuesta varchar2(2),
	CONSTRAINT CP_E_P_U_RESP_OPCION PRIMARY KEY (cod_encuesta,cod_pregunta,cod_opc_multiple,cod_usuario),
	CONSTRAINT CF_E_P_RESP_OPCION FOREIGN KEY (cod_encuesta, cod_pregunta,cod_opc_multiple) REFERENCES P_OPMULTIPLE(cod_encuesta,cod_pregunta,cod_opc_multiple),
	CONSTRAINT CF_US_RESP_OPCION FOREIGN KEY (cod_usuario) REFERENCES USUARIO (cod_usuario)
);
 