<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.office.home.createOrEditLabel" v-text="$t('etaxApp.office.home.createOrEditLabel')">Create or edit a Office</h2>
                <div>
                    <div class="form-group" v-if="office.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="office.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.cstdOfficeType')" for="office-cstdOfficeType">Cstd Office Type</label>
                        <input type="text" class="form-control" name="cstdOfficeType" id="office-cstdOfficeType"
                            :class="{'valid': !$v.office.cstdOfficeType.$invalid, 'invalid': $v.office.cstdOfficeType.$invalid }" v-model="$v.office.cstdOfficeType.$model"  required/>
                        <div v-if="$v.office.cstdOfficeType.$anyDirty && $v.office.cstdOfficeType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.cstdOfficeType.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.office.cstdOfficeType.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.name')" for="office-name">Name</label>
                        <input type="text" class="form-control" name="name" id="office-name"
                            :class="{'valid': !$v.office.name.$invalid, 'invalid': $v.office.name.$invalid }" v-model="$v.office.name.$model"  required/>
                        <div v-if="$v.office.name.$anyDirty && $v.office.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.office.name.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.cstdClassifierCode')" for="office-cstdClassifierCode">Cstd Classifier Code</label>
                        <input type="text" class="form-control" name="cstdClassifierCode" id="office-cstdClassifierCode"
                            :class="{'valid': !$v.office.cstdClassifierCode.$invalid, 'invalid': $v.office.cstdClassifierCode.$invalid }" v-model="$v.office.cstdClassifierCode.$model" />
                        <div v-if="$v.office.cstdClassifierCode.$anyDirty && $v.office.cstdClassifierCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.cstdClassifierCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.effectiveDate')" for="office-effectiveDate">Effective Date</label>
                        <div class="d-flex">
                            <input id="office-effectiveDate" type="datetime-local" class="form-control" name="effectiveDate" :class="{'valid': !$v.office.effectiveDate.$invalid, 'invalid': $v.office.effectiveDate.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.office.effectiveDate.$model)"
                            @change="updateZonedDateTimeField('effectiveDate', $event)"/>
                        </div>
                        <div v-if="$v.office.effectiveDate.$anyDirty && $v.office.effectiveDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.effectiveDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.office.effectiveDate.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.expiryDate')" for="office-expiryDate">Expiry Date</label>
                        <div class="d-flex">
                            <input id="office-expiryDate" type="datetime-local" class="form-control" name="expiryDate" :class="{'valid': !$v.office.expiryDate.$invalid, 'invalid': $v.office.expiryDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.office.expiryDate.$model)"
                            @change="updateZonedDateTimeField('expiryDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.phone')" for="office-phone">Phone</label>
                        <input type="text" class="form-control" name="phone" id="office-phone"
                            :class="{'valid': !$v.office.phone.$invalid, 'invalid': $v.office.phone.$invalid }" v-model="$v.office.phone.$model" />
                        <div v-if="$v.office.phone.$anyDirty && $v.office.phone.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.phone.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.fax')" for="office-fax">Fax</label>
                        <input type="text" class="form-control" name="fax" id="office-fax"
                            :class="{'valid': !$v.office.fax.$invalid, 'invalid': $v.office.fax.$invalid }" v-model="$v.office.fax.$model" />
                        <div v-if="$v.office.fax.$anyDirty && $v.office.fax.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.fax.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.stl')" for="office-stl">Stl</label>
                        <input type="text" class="form-control" name="stl" id="office-stl"
                            :class="{'valid': !$v.office.stl.$invalid, 'invalid': $v.office.stl.$invalid }" v-model="$v.office.stl.$model" />
                        <div v-if="$v.office.stl.$anyDirty && $v.office.stl.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.stl.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 100 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.mngOffice')" for="office-mngOffice">Mng Office</label>
                        <input type="number" class="form-control" name="mngOffice" id="office-mngOffice"
                            :class="{'valid': !$v.office.mngOffice.$invalid, 'invalid': $v.office.mngOffice.$invalid }" v-model.number="$v.office.mngOffice.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.physicalAdr')" for="office-physicalAdr">Physical Adr</label>
                        <textarea class="form-control" name="physicalAdr" id="office-physicalAdr"
                            :class="{'valid': !$v.office.physicalAdr.$invalid, 'invalid': $v.office.physicalAdr.$invalid }" v-model="$v.office.physicalAdr.$model" ></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.postalAadr')" for="office-postalAadr">Postal Aadr</label>
                        <textarea class="form-control" name="postalAadr" id="office-postalAadr"
                            :class="{'valid': !$v.office.postalAadr.$invalid, 'invalid': $v.office.postalAadr.$invalid }" v-model="$v.office.postalAadr.$model" ></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.pinCode')" for="office-pinCode">Pin Code</label>
                        <input type="text" class="form-control" name="pinCode" id="office-pinCode"
                            :class="{'valid': !$v.office.pinCode.$invalid, 'invalid': $v.office.pinCode.$invalid }" v-model="$v.office.pinCode.$model" />
                        <div v-if="$v.office.pinCode.$anyDirty && $v.office.pinCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.pinCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 15 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.cstdWeekWorkingDay')" for="office-cstdWeekWorkingDay">Cstd Week Working Day</label>
                        <input type="text" class="form-control" name="cstdWeekWorkingDay" id="office-cstdWeekWorkingDay"
                            :class="{'valid': !$v.office.cstdWeekWorkingDay.$invalid, 'invalid': $v.office.cstdWeekWorkingDay.$invalid }" v-model="$v.office.cstdWeekWorkingDay.$model" />
                        <div v-if="$v.office.cstdWeekWorkingDay.$anyDirty && $v.office.cstdWeekWorkingDay.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.cstdWeekWorkingDay.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.officeCode')" for="office-officeCode">Office Code</label>
                        <input type="text" class="form-control" name="officeCode" id="office-officeCode"
                            :class="{'valid': !$v.office.officeCode.$invalid, 'invalid': $v.office.officeCode.$invalid }" v-model="$v.office.officeCode.$model" />
                        <div v-if="$v.office.officeCode.$anyDirty && $v.office.officeCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.officeCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 7 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.cstdOfficeSubType')" for="office-cstdOfficeSubType">Cstd Office Sub Type</label>
                        <input type="text" class="form-control" name="cstdOfficeSubType" id="office-cstdOfficeSubType"
                            :class="{'valid': !$v.office.cstdOfficeSubType.$invalid, 'invalid': $v.office.cstdOfficeSubType.$invalid }" v-model="$v.office.cstdOfficeSubType.$model" />
                        <div v-if="$v.office.cstdOfficeSubType.$anyDirty && $v.office.cstdOfficeSubType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.cstdOfficeSubType.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.cstdOfficeFuncType')" for="office-cstdOfficeFuncType">Cstd Office Func Type</label>
                        <input type="text" class="form-control" name="cstdOfficeFuncType" id="office-cstdOfficeFuncType"
                            :class="{'valid': !$v.office.cstdOfficeFuncType.$invalid, 'invalid': $v.office.cstdOfficeFuncType.$invalid }" v-model="$v.office.cstdOfficeFuncType.$model" />
                        <div v-if="$v.office.cstdOfficeFuncType.$anyDirty && $v.office.cstdOfficeFuncType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.office.cstdOfficeFuncType.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.office.ccVersion')" for="office-ccVersion">Cc Version</label>
                        <input type="number" class="form-control" name="ccVersion" id="office-ccVersion"
                            :class="{'valid': !$v.office.ccVersion.$invalid, 'invalid': $v.office.ccVersion.$invalid }" v-model.number="$v.office.ccVersion.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.office.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./office-update.component.ts">
</script>
