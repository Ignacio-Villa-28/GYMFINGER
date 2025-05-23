PGDMP  :                    }            Registro_Gimnasio    17.4    17.4 0    *           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            +           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            ,           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            -           1262    16458    Registro_Gimnasio    DATABASE     y   CREATE DATABASE "Registro_Gimnasio" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'es-MX';
 #   DROP DATABASE "Registro_Gimnasio";
                     postgres    false            �            1255    33053    insertar_folio_bitacora()    FUNCTION     �   CREATE FUNCTION public.insertar_folio_bitacora() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO bitacora (folio) VALUES (NEW.folio);
    RETURN NEW;
END;
$$;
 0   DROP FUNCTION public.insertar_folio_bitacora();
       public               postgres    false            �            1255    33044    insertar_matricula_bitacora()    FUNCTION     �   CREATE FUNCTION public.insertar_matricula_bitacora() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO bitacora (matricula) VALUES (NEW.matricula);
    RETURN NEW;
END;
$$;
 4   DROP FUNCTION public.insertar_matricula_bitacora();
       public               postgres    false            �            1255    33055 #   insertar_matricula_folio_bitacora()    FUNCTION     %  CREATE FUNCTION public.insertar_matricula_folio_bitacora() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  -- Insertar la matrícula en bitacora
  INSERT INTO bitacora (matricula)
  VALUES (NEW.matricula);
  
  -- Insertar el folio en bitacora, asociando el valor de 'folio' de la tabla externos
  -- Asumimos que 'folio' es una columna en la tabla 'externos'
  UPDATE bitacora
  SET folio = e.folio
  FROM externos e
  WHERE e.folio IS NOT NULL AND matricula = NEW.matricula
  AND bitacora.matricula = NEW.matricula;

  RETURN NEW;
END;
$$;
 :   DROP FUNCTION public.insertar_matricula_folio_bitacora();
       public               postgres    false            �            1255    33073     insertar_num_empleado_bitacora()    FUNCTION     �   CREATE FUNCTION public.insertar_num_empleado_bitacora() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO bitacora (num_empleado) VALUES (NEW.num_empleado);
    RETURN NEW;
END;
$$;
 7   DROP FUNCTION public.insertar_num_empleado_bitacora();
       public               postgres    false            �            1255    33066    set2_origen()    FUNCTION     �  CREATE FUNCTION public.set2_origen() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  -- Verificar si el número de la columna 'unico' tiene más de 7 dígitos
  IF LENGTH(CAST(NEW.folio AS TEXT)) > 10 THEN
    RAISE EXCEPTION 'El número de la columna "folio" no puede tener más de 10 dígitos';
  END IF;
  
  -- Asignar 'estudiante' al campo tipo_usuario
  NEW.tipo_usuario := 'externo';
  RETURN NEW;
END;
$$;
 $   DROP FUNCTION public.set2_origen();
       public               postgres    false            �            1255    33076    set3_origen()    FUNCTION     �  CREATE FUNCTION public.set3_origen() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  -- Verificar si el número de la columna 'unico' tiene más de 10 dígitos
  IF LENGTH(CAST(NEW.num_empleado AS TEXT)) > 8 THEN
    RAISE EXCEPTION 'El número de la columna "folio" no puede tener más de 10 dígitos';
  END IF;
  
  -- Asignar 'externo' al campo tipo_usuario
  NEW.tipo_usuario := 'docente_administrativo';
  RETURN NEW;
END;
$$;
 $   DROP FUNCTION public.set3_origen();
       public               postgres    false            �            1255    33046    set_origen()    FUNCTION     �  CREATE FUNCTION public.set_origen() RETURNS trigger
    LANGUAGE plpgsql
    AS $$ 
BEGIN
  -- Verificar si la 'matricula' tiene más de 7 dígitos
  IF LENGTH(CAST(NEW.matricula AS TEXT)) > 7 THEN
    RAISE EXCEPTION 'El número de la columna "matricula" no puede tener más de 7 dígitos';
  
  -- Si la 'matricula' tiene 10 dígitos, asignar 'externo' y limpiar otras columnas
  ELSIF LENGTH(CAST(NEW.matricula AS TEXT)) = 10 THEN
    NEW.tipo_usuario := 'externo';
    NEW.folio := NULL;  -- Limpiar el campo 'folio' para evitar que tenga el valor de la fila anterior
    NEW.otro_campo := NULL;  -- Limpiar otras columnas si es necesario (reemplazar 'otro_campo' con los nombres reales)
  
  -- Si la 'matricula' tiene 7 dígitos, asignar 'estudiante' y limpiar otras columnas
  ELSIF LENGTH(CAST(NEW.matricula AS TEXT)) = 7 THEN
    NEW.tipo_usuario := 'estudiante';
    NEW.folio := NULL;  -- Limpiar el campo 'folio' para evitar que tenga el valor de la fila anterior

  -- Si 'matricula' no contiene números, intentar pasar a 'folio' y asignar 'externo'
  ELSIF NEW.matricula !~ '\d' THEN
    -- Verificar si el campo 'folio' existe y asignar 'externo'
    IF NEW.folio IS NOT NULL THEN
      NEW.tipo_usuario := 'externo';
      NEW.matricula := NULL;  -- Limpiar 'matricula' si no contiene números
    ELSE
      RAISE EXCEPTION 'La matricula no contiene números y no se encuentra un folio relacionado';
    END IF;
  END IF;
  
  RETURN NEW;
END;
$$;
 #   DROP FUNCTION public.set_origen();
       public               postgres    false            �            1259    16464    area    TABLE     d   CREATE TABLE public.area (
    area_adscripcion integer NOT NULL,
    area character varying(20)
);
    DROP TABLE public.area;
       public         heap r       postgres    false            �            1259    16467    area_area_adscripcion_seq    SEQUENCE     �   CREATE SEQUENCE public.area_area_adscripcion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.area_area_adscripcion_seq;
       public               postgres    false    217            .           0    0    area_area_adscripcion_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.area_area_adscripcion_seq OWNED BY public.area.area_adscripcion;
          public               postgres    false    218            �            1259    16484    bitacora    TABLE     ,  CREATE TABLE public.bitacora (
    bitacora integer NOT NULL,
    horayfecha timestamp(6) with time zone,
    tipo_usuario character varying(150),
    matricula character varying(100),
    folio character varying(100),
    num_empleado character varying(100),
    huella character varying(100000)
);
    DROP TABLE public.bitacora;
       public         heap r       postgres    false            �            1259    16468    carrera    TABLE     r   CREATE TABLE public.carrera (
    nivel character varying(20) NOT NULL,
    carrera character varying NOT NULL
);
    DROP TABLE public.carrera;
       public         heap r       postgres    false            �            1259    16472    docente_administrativo    TABLE     $  CREATE TABLE public.docente_administrativo (
    nombre character varying(150) NOT NULL,
    apellido_pat character varying(60) NOT NULL,
    apellido_mat character varying(60) NOT NULL,
    edad integer NOT NULL,
    sexo character varying(20) NOT NULL,
    seguro character varying(20) NOT NULL,
    pais character varying(20) NOT NULL,
    tipo_empleado character varying(20) NOT NULL,
    fechainscripcion character varying(20) NOT NULL,
    area_adscripcion character varying(100) NOT NULL,
    num_empleado character varying(100) NOT NULL
);
 *   DROP TABLE public.docente_administrativo;
       public         heap r       postgres    false            �            1259    16476 
   estudiante    TABLE     �  CREATE TABLE public.estudiante (
    nombre character varying(150) NOT NULL,
    apellido_pat character varying(60) NOT NULL,
    apellido_mat character varying(60) NOT NULL,
    edad integer NOT NULL,
    sexo character varying(20) NOT NULL,
    seguro character varying(20) NOT NULL,
    pais character varying(20) NOT NULL,
    fechainscripcion character varying(20) NOT NULL,
    carrera character varying NOT NULL,
    matricula character varying(100) NOT NULL
);
    DROP TABLE public.estudiante;
       public         heap r       postgres    false            �            1259    16480    externos    TABLE     �  CREATE TABLE public.externos (
    nombre character varying(150) NOT NULL,
    apellido_pat character varying(60) NOT NULL,
    apellido_mat character varying(60) NOT NULL,
    edad integer NOT NULL,
    sexo character varying(20) NOT NULL,
    seguro character varying(20),
    pais character varying(20) NOT NULL,
    procedencia character varying(150) NOT NULL,
    fechainscripcion character varying(20) NOT NULL,
    folio character varying(100) NOT NULL
);
    DROP TABLE public.externos;
       public         heap r       postgres    false            �            1259    24884    usuarios    TABLE     [   CREATE TABLE public.usuarios (
    num_empleado character varying(100),
    origen text
);
    DROP TABLE public.usuarios;
       public         heap r       postgres    false            �            1259    16487    usuarios_usuario_seq    SEQUENCE     �   CREATE SEQUENCE public.usuarios_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.usuarios_usuario_seq;
       public               postgres    false    223            /           0    0    usuarios_usuario_seq    SEQUENCE OWNED BY     N   ALTER SEQUENCE public.usuarios_usuario_seq OWNED BY public.bitacora.bitacora;
          public               postgres    false    224            w           2604    16488    area area_adscripcion    DEFAULT     ~   ALTER TABLE ONLY public.area ALTER COLUMN area_adscripcion SET DEFAULT nextval('public.area_area_adscripcion_seq'::regclass);
 D   ALTER TABLE public.area ALTER COLUMN area_adscripcion DROP DEFAULT;
       public               postgres    false    218    217            x           2604    16493    bitacora bitacora    DEFAULT     u   ALTER TABLE ONLY public.bitacora ALTER COLUMN bitacora SET DEFAULT nextval('public.usuarios_usuario_seq'::regclass);
 @   ALTER TABLE public.bitacora ALTER COLUMN bitacora DROP DEFAULT;
       public               postgres    false    224    223                      0    16464    area 
   TABLE DATA           6   COPY public.area (area_adscripcion, area) FROM stdin;
    public               postgres    false    217   H       %          0    16484    bitacora 
   TABLE DATA           n   COPY public.bitacora (bitacora, horayfecha, tipo_usuario, matricula, folio, num_empleado, huella) FROM stdin;
    public               postgres    false    223   <H       !          0    16468    carrera 
   TABLE DATA           1   COPY public.carrera (nivel, carrera) FROM stdin;
    public               postgres    false    219   �J       "          0    16472    docente_administrativo 
   TABLE DATA           �   COPY public.docente_administrativo (nombre, apellido_pat, apellido_mat, edad, sexo, seguro, pais, tipo_empleado, fechainscripcion, area_adscripcion, num_empleado) FROM stdin;
    public               postgres    false    220   �J       #          0    16476 
   estudiante 
   TABLE DATA           �   COPY public.estudiante (nombre, apellido_pat, apellido_mat, edad, sexo, seguro, pais, fechainscripcion, carrera, matricula) FROM stdin;
    public               postgres    false    221   �J       $          0    16480    externos 
   TABLE DATA           �   COPY public.externos (nombre, apellido_pat, apellido_mat, edad, sexo, seguro, pais, procedencia, fechainscripcion, folio) FROM stdin;
    public               postgres    false    222   :K       '          0    24884    usuarios 
   TABLE DATA           8   COPY public.usuarios (num_empleado, origen) FROM stdin;
    public               postgres    false    225   WK       0           0    0    area_area_adscripcion_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.area_area_adscripcion_seq', 1, false);
          public               postgres    false    218            1           0    0    usuarios_usuario_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.usuarios_usuario_seq', 53, true);
          public               postgres    false    224            z           2606    16495    area pk_id_area_adscripcion 
   CONSTRAINT     g   ALTER TABLE ONLY public.area
    ADD CONSTRAINT pk_id_area_adscripcion PRIMARY KEY (area_adscripcion);
 E   ALTER TABLE ONLY public.area DROP CONSTRAINT pk_id_area_adscripcion;
       public                 postgres    false    217            �           2606    16507    bitacora pk_id_bitacora 
   CONSTRAINT     [   ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT pk_id_bitacora PRIMARY KEY (bitacora);
 A   ALTER TABLE ONLY public.bitacora DROP CONSTRAINT pk_id_bitacora;
       public                 postgres    false    223            |           2606    16571    carrera pk_id_carrera 
   CONSTRAINT     X   ALTER TABLE ONLY public.carrera
    ADD CONSTRAINT pk_id_carrera PRIMARY KEY (carrera);
 ?   ALTER TABLE ONLY public.carrera DROP CONSTRAINT pk_id_carrera;
       public                 postgres    false    219            �           2606    16620    externos pk_id_folio 
   CONSTRAINT     U   ALTER TABLE ONLY public.externos
    ADD CONSTRAINT pk_id_folio PRIMARY KEY (folio);
 >   ALTER TABLE ONLY public.externos DROP CONSTRAINT pk_id_folio;
       public                 postgres    false    222            �           2606    16613    estudiante pk_id_matricula 
   CONSTRAINT     _   ALTER TABLE ONLY public.estudiante
    ADD CONSTRAINT pk_id_matricula PRIMARY KEY (matricula);
 D   ALTER TABLE ONLY public.estudiante DROP CONSTRAINT pk_id_matricula;
       public                 postgres    false    221            ~           2606    16629 )   docente_administrativo pk_id_num_empleado 
   CONSTRAINT     q   ALTER TABLE ONLY public.docente_administrativo
    ADD CONSTRAINT pk_id_num_empleado PRIMARY KEY (num_empleado);
 S   ALTER TABLE ONLY public.docente_administrativo DROP CONSTRAINT pk_id_num_empleado;
       public                 postgres    false    220            �           2620    33067    bitacora set2_origen_trigger    TRIGGER     x   CREATE TRIGGER set2_origen_trigger BEFORE INSERT ON public.bitacora FOR EACH ROW EXECUTE FUNCTION public.set2_origen();
 5   DROP TRIGGER set2_origen_trigger ON public.bitacora;
       public               postgres    false    223    226            �           2620    33077    bitacora set3_origen_trigger    TRIGGER     x   CREATE TRIGGER set3_origen_trigger BEFORE INSERT ON public.bitacora FOR EACH ROW EXECUTE FUNCTION public.set3_origen();
 5   DROP TRIGGER set3_origen_trigger ON public.bitacora;
       public               postgres    false    223    230            �           2620    33047    bitacora set_origen_trigger    TRIGGER     v   CREATE TRIGGER set_origen_trigger BEFORE INSERT ON public.bitacora FOR EACH ROW EXECUTE FUNCTION public.set_origen();
 4   DROP TRIGGER set_origen_trigger ON public.bitacora;
       public               postgres    false    242    223            �           2620    33065    externos trigger_insertar_folio    TRIGGER     �   CREATE TRIGGER trigger_insertar_folio AFTER INSERT ON public.externos FOR EACH ROW EXECUTE FUNCTION public.insertar_folio_bitacora();
 8   DROP TRIGGER trigger_insertar_folio ON public.externos;
       public               postgres    false    227    222            �           2620    33064 %   estudiante trigger_insertar_matricula    TRIGGER     �   CREATE TRIGGER trigger_insertar_matricula AFTER INSERT ON public.estudiante FOR EACH ROW EXECUTE FUNCTION public.insertar_matricula_bitacora();
 >   DROP TRIGGER trigger_insertar_matricula ON public.estudiante;
       public               postgres    false    221    228            �           2620    33075 4   docente_administrativo trigger_insertar_num_empleado    TRIGGER     �   CREATE TRIGGER trigger_insertar_num_empleado AFTER INSERT ON public.docente_administrativo FOR EACH ROW EXECUTE FUNCTION public.insertar_num_empleado_bitacora();
 M   DROP TRIGGER trigger_insertar_num_empleado ON public.docente_administrativo;
       public               postgres    false    220    229            �           2606    33059    bitacora fk_id_folio    FK CONSTRAINT     �   ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT fk_id_folio FOREIGN KEY (folio) REFERENCES public.externos(folio) NOT VALID;
 >   ALTER TABLE ONLY public.bitacora DROP CONSTRAINT fk_id_folio;
       public               postgres    false    223    4738    222            �           2606    16614    bitacora fk_id_matricula    FK CONSTRAINT     �   ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT fk_id_matricula FOREIGN KEY (matricula) REFERENCES public.estudiante(matricula) NOT VALID;
 B   ALTER TABLE ONLY public.bitacora DROP CONSTRAINT fk_id_matricula;
       public               postgres    false    221    223    4736            �           2606    33068    bitacora fk_id_num_empleado    FK CONSTRAINT     �   ALTER TABLE ONLY public.bitacora
    ADD CONSTRAINT fk_id_num_empleado FOREIGN KEY (num_empleado) REFERENCES public.docente_administrativo(num_empleado) NOT VALID;
 E   ALTER TABLE ONLY public.bitacora DROP CONSTRAINT fk_id_num_empleado;
       public               postgres    false    220    4734    223                  x������ � �      %   R  x�u�Ao�@���+*�Ua*JeCRf` H�J� hm)�~���q�����?YϵK���Uq�H���*ٖ�9���^٭�la�w¹ލe=؍WT��eՍ����t~��|�ދm�=V���ܬ�e�n3~�ē�Ϋ<�[�6m����*���C�5 ~ \j� <�	���/>+;[�I����� �.)���$�q��5|4Ns~��eN�/L��S~^���{	y3�0O�&��MUȪ�n�<⁃�Y	��yH�q���}��H�}���M#U�����(��X��zL��8vN>�����@yiv)���
�웹Y��
pnV�L0�-������ 	8����g5sL����dŸ��&G9x��,0�$K���=���9�{��`��%\\�0�IݠH���4� ;.�b���K$� �#i����Ρ��,�݁��;׻"��_;��a�]A�X�Y{�P�z�t�9�A�nt�s��j|�mS�p��6E9��!J0���a�������h	���r�.�$a��I�c����V���-Nm��t���_nM��6���V����j�\�����      !      x������ � �      "      x������ � �      #   R   x��A
� ���]��FG<L�R�SH�������:�����v8��u8��?��&K�R�,	��� /}$��Ƙj<(      $      x������ � �      '   D   x�342�L�ON�+I�OL����,.)J,�,��224�013�L-.)M�L��Y��!��qqq ��     