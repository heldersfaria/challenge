package br.com.dextra.potter.mapper;

import br.com.dextra.potter.config.UserCadastroProperties;
import br.com.dextra.potter.dto.InformacoesParaCadastroUsuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DadosCadastroDeUsuarioMapper {

    InformacoesParaCadastroUsuario montarCadastro(UserCadastroProperties userCadastroProperties);

}
