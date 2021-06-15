package es.firmae.demo.interfaces.shared;

public class SwaggerDocumentation {

    public static final String existNickname_op_summary = "Comprobar nickname";
    public static final String existNickname_op_description = "Verificar existencia de un nickname.";
    public static final String existNickname_op_resp_description = "Retorna TRUE o FALSE teniendo en cuenta si existe o no el nickname especificado.";

    public static final String receiveProfiles_op_summary = "Recibir perfiles";
    public static final String receiveProfiles_op_description = "Recibir perfiles de usuarios favoritos y almacenarlos en memoria con H2.";
    public static final String receiveProfiles_head_description = "Perfiles de usuarios favoritos almacenados satisfacoriamente";

    public static final String getProfile_op_summary = "Consultar lista guardada";
    public static final String getProfile_op_description = "Consultar lista guardada por el nickname especificado.";
    public static final String getProfile_resp_description = "Lista guardada de usuarios favoritos por el nickname del perfil especificado.";
}
