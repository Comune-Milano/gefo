package it.dmi.dmifonbe.dmifonpro.service.impl;

import it.dmi.dmifonbe.dmifonamm.entities.AmmAll;
import it.dmi.dmifonbe.dmifonamm.entities.AmmFil;
import it.dmi.dmifonbe.dmifonamm.entities.AmmPar;
import it.dmi.dmifonbe.dmifonamm.model.AllegatoResponse;
import it.dmi.dmifonbe.dmifonamm.model.AmmFilBase64;
import it.dmi.dmifonbe.dmifonamm.model.CaricaAllegatoModel;
import it.dmi.dmifonbe.dmifonamm.repository.AllegatoRepository;
import it.dmi.dmifonbe.dmifonamm.repository.AmmFilRepository;
import it.dmi.dmifonbe.dmifonamm.repository.ParametriRepository;
import it.dmi.dmifonbe.dmifonamm.service.UtilService;
import it.dmi.dmifonbe.dmifonpro.entities.ProAva;
import it.dmi.dmifonbe.dmifonpro.entities.ProDdr;
import it.dmi.dmifonbe.dmifonpro.entities.ProPro;
import it.dmi.dmifonbe.dmifonpro.repository.AvanzamentoRepository;
import it.dmi.dmifonbe.dmifonpro.repository.DdrRepository;
import it.dmi.dmifonbe.dmifonpro.repository.ProgettoRepository;
import it.dmi.dmifonbe.dmifonpro.service.AllegatoService;
import it.dmi.dmifonbe.model.Parameters;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllegatoServiceImpl implements AllegatoService {

    @Autowired
    AllegatoRepository allegatoRepository;

    @Autowired
    AmmFilRepository ammFilRepository;

    @Autowired
    ProgettoRepository progettoRepository;

    @Autowired
    AvanzamentoRepository avanzamentoRepository;

    @Autowired
    DdrRepository ddrRepository;

    @Autowired
    ParametriRepository parametriRepository;

    @Autowired
    UtilService utilService;

    @Override
    @Transactional
    public AllegatoResponse getAllegato(Integer idEnt, String tipEnt) throws MicroServicesException {
        if (
            !tipEnt.equalsIgnoreCase("PRO") && !tipEnt.equalsIgnoreCase("AVA") && !tipEnt.equalsIgnoreCase("DDR")
        ) throw new MicroServicesException(ErrorMessages.TIPENT_NOT_ALLOWED.getUserMessage(), ErrorMessages.TIPENT_NOT_ALLOWED.getCode());
        List<AmmAll> ammAlls = allegatoRepository.findByIdentAndTipent(idEnt.longValue(), tipEnt);
        AllegatoResponse response = new AllegatoResponse();
        String codice = "";
        String descrizione = "";
        if (tipEnt.equalsIgnoreCase("PRO")) {
            Optional<ProPro> proProOptional = progettoRepository.findById(idEnt);
            if (proProOptional.isPresent()) {
                codice = proProOptional.get().getCodpro();
                descrizione = proProOptional.get().getDespro();
            }
        } else if (tipEnt.equalsIgnoreCase("AVA")) {
            Optional<ProAva> proAvaOptional = avanzamentoRepository.findById(idEnt);
            if (proAvaOptional.isPresent()) {
                ProPro proPro = proAvaOptional.get().getProProByIdPro();
                codice = proPro.getCodpro();
                descrizione = proPro.getDespro();
            }
        } else if (tipEnt.equalsIgnoreCase("DDR")) {
            Optional<ProDdr> proDdrOptional = ddrRepository.findById(idEnt);
            if (proDdrOptional.isPresent()) {
                codice = proDdrOptional.get().getCodddr();
                descrizione = proDdrOptional.get().getDesddr();
            }
        }
        response.setCodice(codice);
        response.setDescrizione(descrizione);
        response.setAllegati(ammAlls);

        return response;
    }

    @Override
    public AmmAll caricaFile(CaricaAllegatoModel allegatoDaCaricare) throws MicroServicesException {
        this.checkUploadAllegato(allegatoDaCaricare);
        AmmFil ammFilToUpload = new AmmFil();
        ammFilToUpload.setFile(Base64.getDecoder().decode(allegatoDaCaricare.getFile().getFile()));
        ammFilToUpload.setDataType(allegatoDaCaricare.getFile().getDataType());
        utilService.setInfoInsertRow(ammFilToUpload);
        AmmFil ammFilUploaded = ammFilRepository.saveAndFlush(ammFilToUpload);
        allegatoDaCaricare.getAllegato().setIdFil(ammFilUploaded.getId().longValue());
        utilService.setInfoInsertRow(allegatoDaCaricare.getAllegato());
        return allegatoRepository.saveAndFlush(allegatoDaCaricare.getAllegato());
    }

    private void checkUploadAllegato(CaricaAllegatoModel allegatoDaCaricare) throws MicroServicesException {
        this.checkInsertAllegato(allegatoDaCaricare.getAllegato());
        this.checkInsertFile(allegatoDaCaricare.getFile());
    }

    private void checkInsertFile(AmmFilBase64 file) throws MicroServicesException {
        if (
            !file.getFile().startsWith(Parameters.PDF.getValue()) &&
            !file.getFile().startsWith(Parameters.XlsxDocxZip.getValue()) &&
            !file.getFile().startsWith(Parameters.XlsDoc.getValue()) &&
            !file.getFile().startsWith(Parameters.JPG.getValue())
        ) throw new MicroServicesException(
            ErrorMessages.ALLEGATO_EXTENSION_NOT_VALID.getUserMessage(),
            ErrorMessages.ALLEGATO_EXTENSION_NOT_VALID.getCode()
        );

        Optional<AmmPar> sizeMaxOptional = parametriRepository.getAmmParByCodiceIgnoreCase("AllegatiMaxSizeMB");
        if (sizeMaxOptional.isPresent()) {
            Long sizeMaxInByte = Long.valueOf(sizeMaxOptional.get().getValore()) * 1000000;
            byte[] decodedFile = Base64.getDecoder().decode(file.getFile());
            if (decodedFile.length > sizeMaxInByte) throw new MicroServicesException(
                ErrorMessages.UPLOAD_MAXSIZE.getUserMessage() + sizeMaxOptional.get().getValore() + "MB",
                ErrorMessages.UPLOAD_MAXSIZE.getCode()
            );
        }
    }

    private void checkInsertAllegato(AmmAll allegato) throws MicroServicesException {
        if (
            !allegato.getTipent().equalsIgnoreCase("PRO") &&
            !allegato.getTipent().equalsIgnoreCase("AVA") &&
            !allegato.getTipent().equalsIgnoreCase("DDR")
        ) throw new MicroServicesException(ErrorMessages.TIPENT_NOT_ALLOWED.getUserMessage(), ErrorMessages.TIPENT_NOT_ALLOWED.getCode());
        if (allegato.getTipent().equalsIgnoreCase("PRO")) {
            if (!progettoRepository.existsById(allegato.getIdent().intValue())) {
                throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_PRO.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_PRO.getCode()
                );
            }
        } else if (allegato.getTipent().equalsIgnoreCase("AVA")) {
            if (!avanzamentoRepository.existsById(allegato.getIdent().intValue())) {
                throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_AVA.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_AVA.getCode()
                );
            }
        } else if (!allegato.getTipent().equalsIgnoreCase("DDR")) {
            if (!ddrRepository.existsById(allegato.getIdent().intValue())) {
                throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_DDR.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_DDR.getCode()
                );
            }
        }
        if (
            allegatoRepository.findByIdentAndTipentAndNomfil(allegato.getIdent(), allegato.getTipent(), allegato.getNomfil()).isPresent()
        ) throw new MicroServicesException(
            ErrorMessages.ALLEGATO_ALREADY_PRESENT.getUserMessage(),
            ErrorMessages.ALLEGATO_ALREADY_PRESENT.getCode()
        );

        if (allegato.getId() != null && allegato.getId() != 0) throw new MicroServicesException(
            ErrorMessages.ALLEGATO_INSERT_WITH_ID.getUserMessage(),
            ErrorMessages.ALLEGATO_INSERT_WITH_ID.getCode()
        );
    }

    @Override
    public AmmFilBase64 scaricaFile(Integer idFile) throws MicroServicesException {
        Optional<AmmFil> ammFilOptional = ammFilRepository.findById(idFile);
        if (ammFilOptional.isPresent()) {
            AmmFilBase64 result = new AmmFilBase64();
            result.setId(ammFilOptional.get().getId());
            result.setFile(
                this.addApplicationInfoOnBase64(
                        ammFilOptional.get().getDataType(),
                        Base64.getEncoder().encodeToString(ammFilOptional.get().getFile())
                    )
            );
            return result;
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }

    private String addApplicationInfoOnBase64(String dataType, String encodeToString) {
        return dataType + "," + encodeToString;
    }

    @Override
    public void modificaAllegato(AmmAll allegatoDaModificare) throws MicroServicesException {
        this.checkModificaAllegato(allegatoDaModificare);
        Optional<AmmAll> ammAllOriginaleOptional = allegatoRepository.findById(allegatoDaModificare.getId());
        if (ammAllOriginaleOptional.isPresent()) {
            AmmAll ammAllOriginale = ammAllOriginaleOptional.get();
            allegatoDaModificare.setDtCreate(ammAllOriginale.getDtCreate());
            allegatoDaModificare.setUsrCreate(ammAllOriginale.getUsrCreate());
            utilService.setInfoUpdateRow(allegatoDaModificare);
        }
        allegatoRepository.saveAndFlush(allegatoDaModificare);
    }

    private void checkModificaAllegato(AmmAll allegatoDaModificare) throws MicroServicesException {
        if (
            !allegatoDaModificare.getTipent().equalsIgnoreCase("PRO") &&
            !allegatoDaModificare.getTipent().equalsIgnoreCase("AVA") &&
            !allegatoDaModificare.getTipent().equalsIgnoreCase("DDR")
        ) throw new MicroServicesException(ErrorMessages.TIPENT_NOT_ALLOWED.getUserMessage(), ErrorMessages.TIPENT_NOT_ALLOWED.getCode());
        if (allegatoDaModificare.getId() == 0) throw new MicroServicesException(
            ErrorMessages.ALLEGATO_EDIT_NO_ID.getUserMessage(),
            ErrorMessages.ALLEGATO_EDIT_NO_ID.getCode()
        );
        if (!allegatoRepository.existsById(allegatoDaModificare.getId())) throw new MicroServicesException(
            ErrorMessages.NO_DATA_FOUND.getUserMessage(),
            ErrorMessages.NO_DATA_FOUND.getCode()
        );
        if (allegatoDaModificare.getTipent().equalsIgnoreCase("PRO")) {
            if (!progettoRepository.existsById(allegatoDaModificare.getIdent().intValue())) {
                throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_PRO.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_PRO.getCode()
                );
            }
        } else if (allegatoDaModificare.getTipent().equalsIgnoreCase("AVA")) {
            if (!avanzamentoRepository.existsById(allegatoDaModificare.getIdent().intValue())) {
                throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_AVA.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_AVA.getCode()
                );
            }
        } else if (!allegatoDaModificare.getTipent().equalsIgnoreCase("DDR")) {
            if (!ddrRepository.existsById(allegatoDaModificare.getIdent().intValue())) {
                throw new MicroServicesException(
                    ErrorMessages.NO_DATA_FOUND_DDR.getUserMessage(),
                    ErrorMessages.NO_DATA_FOUND_DDR.getCode()
                );
            }
        }
        Optional<AmmAll> allegatoOriginaleOptional = allegatoRepository.findById(allegatoDaModificare.getId());
        if (allegatoOriginaleOptional.isPresent()) {
            AmmAll allegatoOriginale = allegatoOriginaleOptional.get();
            if (
                !allegatoOriginale.getTipent().equals(allegatoDaModificare.getTipent()) ||
                !allegatoOriginale.getNomfil().equals(allegatoDaModificare.getNomfil()) ||
                !allegatoOriginale.getIdFil().equals(allegatoDaModificare.getIdFil()) ||
                !allegatoOriginale.getIdent().equals(allegatoDaModificare.getIdent())
            ) throw new MicroServicesException(
                ErrorMessages.ALLEGATO_NOT_EDITABLE.getUserMessage(),
                ErrorMessages.ALLEGATO_NOT_EDITABLE.getCode()
            );
        }
    }

    @Override
    public void cancellaAllegato(Integer idAllegato) throws MicroServicesException {
        Optional<AmmAll> allegatoOptional = allegatoRepository.findById(idAllegato);
        if (allegatoOptional.isPresent()) {
            Long idFile = allegatoOptional.get().getIdFil();
            if (ammFilRepository.existsById(idFile.intValue())) ammFilRepository.deleteById(
                idFile.intValue()
            ); else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
        } else throw new MicroServicesException(ErrorMessages.NO_DATA_FOUND.getUserMessage(), ErrorMessages.NO_DATA_FOUND.getCode());
    }
}
