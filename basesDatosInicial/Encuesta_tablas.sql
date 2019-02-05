--eliminar el usuario si existe 

drop user "ENCUESTA_DB" cascade;

--CREATE tablespace MyTableSpace datafile 'C:\oraclexe\app\oracle\oradata\XE\MyTableSpace.DBF' size 30M;

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
 CREATE TABLE ENCUESTA_USUARIO(
	cod_encuesta number(5),
	cod_usuario varchar2(10),
	CONSTRAINT CP_ENC_USUA PRIMARY KEY (cod_encuesta, cod_usuario),
	CONSTRAINT CF_COD_ENCUESTA FOREIGN KEY (cod_encuesta) REFERENCES ENCUESTA(cod_encuesta),
	CONSTRAINT CF_COD_USUARIO FOREIGN KEY (cod_usuario) REFERENCES USUARIO(cod_usuario)
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


CREATE OR REPLACE PROCEDURE validarCedulaUsuario(cedula in varchar2, valido out number)
is
verificador number;
finalS number;
suma number;
aux number;
begin
    suma :=0;
    aux:=0;
    valido:=0;
    verificador:=to_number(substr(cedula,-1));
    if to_number(substr(cedula,1,2))<24 then
        if to_number(substr(cedula,3,1))<6 then
            FOR i IN 1..9 LOOP
                if MOD(i,2) = 0 then
                    aux :=TO_NUMBER((SUBSTR(cedula,i,1)));
                    if aux >=10 then
                        aux :=aux -9;
                    end if;
                else
                    aux := TO_NUMBER((SUBSTR(cedula,i,1)))*2;
                    if aux >=10 then
                        aux :=aux -9;
                    end if;
                end if;
                suma := suma + aux;
                aux:=0;
            END LOOP;
        end if;
        finalS:=suma+verificador;
        if mod(finalS,10)=0 then
            DBMS_OUTPUT.PUT_LINE ('Cedula Correcta');
            valido:=1;
        else
            DBMS_OUTPUT.PUT_LINE ('Cedula Incorrecta');
            valido:=0;
        end if;
    else
        DBMS_OUTPUT.PUT_LINE ('Cedula Incorrecta');
        valido:=0;
    end if;
end validarCedulaUsuario;

/
CREATE OR REPLACE PROCEDURE validarCedula(cedula in varchar2, valido out number)
is
verificador number;
finalS number;
suma number;
aux number;
begin
    suma :=0;
    aux:=0;
    valido:=0;
    verificador:=to_number(substr(cedula,-1));
    if to_number(substr(cedula,1,2))<24 then
        if to_number(substr(cedula,3,1))<6 then
            FOR i IN 1..9 LOOP
                if MOD(i,2) = 0 then
                    aux :=TO_NUMBER((SUBSTR(cedula,i,1)));
                    if aux >=10 then
                        aux :=aux -9;
                    end if;
                else
                    aux := TO_NUMBER((SUBSTR(cedula,i,1)))*2;
                    if aux >=10 then
                        aux :=aux -9;
                    end if;
                end if;
                suma := suma + aux;
                aux:=0;
            END LOOP;
        end if;
        finalS:=suma+verificador;
        if mod(finalS,10)=0 then
            DBMS_OUTPUT.PUT_LINE ('Cedula Correcta');
            valido:=1;
        else
            DBMS_OUTPUT.PUT_LINE ('Cedula Incorrecta');
            valido:=0;
        end if;
    else
        DBMS_OUTPUT.PUT_LINE ('Cedula Incorrecta');
        valido:=0;
    end if;
end validarCedula;
/

CREATE OR REPLACE PROCEDURE ADD_DATOS_USUARIO_ENCUESTA(cod_usuario in VARCHAR2, cod_encuesta in VARCHAR2)
AS 
BEGIN 
INSERT INTO ENCUESTA_USUARIO VALUES (cod_usuario, cod_encuesta); 
END ADD_DATOS_USUARIO_ENCUESTA;
/