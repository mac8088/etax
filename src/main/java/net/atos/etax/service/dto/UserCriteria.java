package net.atos.etax.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link net.atos.etax.domain.User2} entity. This class is used
 * in {@link net.atos.etax.web.rest.User2Resource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-2-s?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter login;

    private StringFilter passwordHash;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter email;

    private StringFilter imageRrl;

    private BooleanFilter activated;

    private StringFilter langKey;

    private StringFilter activationKey;

    private StringFilter resetKey;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter resetDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    public UserCriteria(){
    }

    public UserCriteria(UserCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.passwordHash = other.passwordHash == null ? null : other.passwordHash.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.imageRrl = other.imageRrl == null ? null : other.imageRrl.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.langKey = other.langKey == null ? null : other.langKey.copy();
        this.activationKey = other.activationKey == null ? null : other.activationKey.copy();
        this.resetKey = other.resetKey == null ? null : other.resetKey.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.resetDate = other.resetDate == null ? null : other.resetDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
    }

    @Override
    public UserCriteria copy() {
        return new UserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(StringFilter passwordHash) {
        this.passwordHash = passwordHash;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getImageRrl() {
        return imageRrl;
    }

    public void setImageRrl(StringFilter imageRrl) {
        this.imageRrl = imageRrl;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getLangKey() {
        return langKey;
    }

    public void setLangKey(StringFilter langKey) {
        this.langKey = langKey;
    }

    public StringFilter getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(StringFilter activationKey) {
        this.activationKey = activationKey;
    }

    public StringFilter getResetKey() {
        return resetKey;
    }

    public void setResetKey(StringFilter resetKey) {
        this.resetKey = resetKey;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public InstantFilter getResetDate() {
        return resetDate;
    }

    public void setResetDate(InstantFilter resetDate) {
        this.resetDate = resetDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserCriteria that = (UserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(login, that.login) &&
            Objects.equals(passwordHash, that.passwordHash) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(imageRrl, that.imageRrl) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(langKey, that.langKey) &&
            Objects.equals(activationKey, that.activationKey) &&
            Objects.equals(resetKey, that.resetKey) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(resetDate, that.resetDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        login,
        passwordHash,
        firstName,
        lastName,
        email,
        imageRrl,
        activated,
        langKey,
        activationKey,
        resetKey,
        createdBy,
        createdDate,
        resetDate,
        lastModifiedBy,
        lastModifiedDate
        );
    }

    @Override
    public String toString() {
        return "User2Criteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (passwordHash != null ? "passwordHash=" + passwordHash + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (imageRrl != null ? "imageRrl=" + imageRrl + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (langKey != null ? "langKey=" + langKey + ", " : "") +
                (activationKey != null ? "activationKey=" + activationKey + ", " : "") +
                (resetKey != null ? "resetKey=" + resetKey + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (resetDate != null ? "resetDate=" + resetDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            "}";
    }

}
