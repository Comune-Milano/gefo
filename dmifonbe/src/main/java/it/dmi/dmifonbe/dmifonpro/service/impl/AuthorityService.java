package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmUte;
import it.dmi.dmifonbe.dmifonamm.entities.AmmUteruo;
import it.dmi.dmifonbe.dmifonamm.model.Permesso;
import it.dmi.dmifonbe.dmifonamm.repository.AmmUteRuoRepository;
import it.dmi.dmifonbe.dmifonamm.service.impl.AmmPerService;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthorityService {

    @Autowired
    AmmUteRuoRepository ammUteRuoRepository;

    @Autowired
    AmmPerService ammPerService;

    @Transactional
    public boolean hasPermission(String funzione, Integer idUteruo) throws MicroServicesException {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getClaim("preferred_username");
        List<Permesso> permessi = ammPerService.loadRuoloFunzioni();
        Optional<AmmUteruo> ammUteruoOptional = ammUteRuoRepository.findById(idUteruo);
        if (ammUteruoOptional.isPresent()) {
            AmmUteruo ammUteruo = ammUteruoOptional.get();
            Hibernate.initialize(ammUteruo.getAmmUteByIdUte());
            Hibernate.initialize(ammUteruo.getAmmRuoByIdRuo());
            Hibernate.initialize(ammUteruo.getAmmDirByIdDir());
            AmmUte ammUte = ammUteruo.getAmmUteByIdUte();
            if (!ammUte.getUsername().equalsIgnoreCase(username)) throw new MicroServicesException(
                ErrorMessages.USER_NOT_CORRESPONDING.getUserMessage(),
                ErrorMessages.USER_NOT_CORRESPONDING.getCode()
            );
            if (!ammUte.getAbilitato()) throw new MicroServicesException(
                ErrorMessages.USER_NOT_ABILITATE.getUserMessage(),
                ErrorMessages.USER_NOT_ABILITATE.getCode()
            );
            Permesso permesso = new Permesso(Long.valueOf(ammUteruo.getAmmRuoByIdRuo().getId()), funzione);
            if (permessi == null || permessi.isEmpty() || !permessi.contains(permesso)) throw new MicroServicesException(
                ErrorMessages.NO_FUNCTION_ASSOCIATED.getUserMessage(),
                ErrorMessages.NO_FUNCTION_ASSOCIATED.getCode()
            );
        } else throw new MicroServicesException(
            ErrorMessages.AMMUTERUO_NOT_PRESENT.getUserMessage(),
            ErrorMessages.AMMUTERUO_NOT_PRESENT.getCode()
        );

        return true;
    }
}
