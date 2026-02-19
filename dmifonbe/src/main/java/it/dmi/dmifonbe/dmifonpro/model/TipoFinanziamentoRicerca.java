package it.dmi.dmifonbe.dmifonpro.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.dmi.dmifonbe.dmifonpro.service.annotations.HtmlEscape;
import it.dmi.dmifonbe.model.messages.ErrorMessages;
import it.dmi.dmifonbe.web.rest.errors.exceptions.MicroServicesException;
import java.io.Serializable;

@JsonInclude(value = Include.NON_NULL)
@JsonSerialize
public class TipoFinanziamentoRicerca extends AutocompleteRicerca implements Serializable {

    private static final long serialVersionUID = 1978760595385556376L;

    @HtmlEscape
    private String tipLiv;

    private Integer liv1;
    private Integer liv2;
    private Integer liv3;
    private Integer liv4;
    private Integer liv5;
    private boolean calcolaTotali;

    public Integer getLiv5() {
        return liv5;
    }

    public void setLiv5(Integer liv5) {
        this.liv5 = liv5;
    }

    public String getTipLiv() {
        return tipLiv;
    }

    public void setTipLiv(String tipLiv) {
        this.tipLiv = tipLiv;
    }

    public Integer getLiv1() {
        return liv1;
    }

    public void setLiv1(Integer liv1) {
        this.liv1 = liv1;
    }

    public Integer getLiv2() {
        return liv2;
    }

    public void setLiv2(Integer liv2) {
        this.liv2 = liv2;
    }

    public Integer getLiv3() {
        return liv3;
    }

    public void setLiv3(Integer liv3) {
        this.liv3 = liv3;
    }

    public Integer getLiv4() {
        return liv4;
    }

    public void setLiv4(Integer liv4) {
        this.liv4 = liv4;
    }

    public boolean getCalcolaTotali() {
        return calcolaTotali;
    }

    public void setCalcolaTotali(boolean calcolaTotali) {
        this.calcolaTotali = calcolaTotali;
    }

    public boolean checkNull() {
        return (
            super.getAutocomplete() == null &&
            this.liv1 == null &&
            this.liv2 == null &&
            this.liv3 == null &&
            this.liv4 == null &&
            this.tipLiv == null
        );
    }

    public void validate() throws MicroServicesException {
        if (
            (this.getLiv1() != null && this.getLiv1() < 0) ||
            (this.getLiv2() != null && this.getLiv2() < 0) ||
            (this.getLiv3() != null && this.getLiv3() < 0) ||
            (this.getLiv4() != null && this.getLiv4() < 0) ||
            (this.getLiv5() != null && this.getLiv5() < 0)
        ) {
            throw new MicroServicesException(
                ErrorMessages.NEGATIVE_LIV_FILTER.getUserMessage(),
                ErrorMessages.NEGATIVE_LIV_FILTER.getCode()
            );
        }
        if (
            this.getTipLiv() != null &&
            !this.getTipLiv().isEmpty() &&
            (
                !this.getTipLiv().equals("01") &&
                !this.getTipLiv().equals("02") &&
                !this.getTipLiv().equals("03") &&
                !this.getTipLiv().equals("04") &&
                !this.getTipLiv().equals("05") &&
                !this.getTipLiv().equals("06")
            )
        ) {
            throw new MicroServicesException(ErrorMessages.INVALID_TIPLIV.getUserMessage(), ErrorMessages.INVALID_TIPLIV.getCode());
        }
        if (this.getTipLiv() != null && !this.getTipLiv().isEmpty()) {
            if (this.getTipLiv().equals("01")) {
                if (
                    (this.getLiv2() != null && this.getLiv2() != 0) ||
                    (this.getLiv3() != null && this.getLiv3() != 0) ||
                    (this.getLiv4() != null && this.getLiv4() != 0)
                ) {
                    throw new MicroServicesException(
                        ErrorMessages.INVALID_LIV_FILTER.getUserMessage(),
                        ErrorMessages.INVALID_LIV_FILTER.getCode()
                    );
                }
            } else if (this.getTipLiv().equals("02")) {
                if ((this.getLiv3() != null && this.getLiv3() != 0) || (this.getLiv4() != null && this.getLiv4() != 0)) {
                    throw new MicroServicesException(
                        ErrorMessages.INVALID_LIV_FILTER.getUserMessage(),
                        ErrorMessages.INVALID_LIV_FILTER.getCode()
                    );
                }
            } else if (this.getTipLiv().equals("03")) {
                if ((this.getLiv4() != null && this.getLiv4() != 0)) {
                    throw new MicroServicesException(
                        ErrorMessages.INVALID_LIV_FILTER.getUserMessage(),
                        ErrorMessages.INVALID_LIV_FILTER.getCode()
                    );
                }
            }
            //non aggiungo i controlli non essenziali per livello 4, 5 e 6
        }
    }
}
