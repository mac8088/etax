<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.privilege.home.createOrEditLabel" v-text="$t('etaxApp.privilege.home.createOrEditLabel')">Create or edit a Privilege</h2>
                <div>
                    <div class="form-group" v-if="privilege.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="privilege.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.privilege.appCode')" for="privilege-appCode">App Code</label>
                        <input type="text" class="form-control" name="appCode" id="privilege-appCode"
                            :class="{'valid': !$v.privilege.appCode.$invalid, 'invalid': $v.privilege.appCode.$invalid }" v-model="$v.privilege.appCode.$model"  required/>
                        <div v-if="$v.privilege.appCode.$anyDirty && $v.privilege.appCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.privilege.appCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.privilege.appCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.privilege.userName')" for="privilege-userName">User Name</label>
                        <input type="text" class="form-control" name="userName" id="privilege-userName"
                            :class="{'valid': !$v.privilege.userName.$invalid, 'invalid': $v.privilege.userName.$invalid }" v-model="$v.privilege.userName.$model"  required/>
                        <div v-if="$v.privilege.userName.$anyDirty && $v.privilege.userName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.privilege.userName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.privilege.userName.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.privilege.profileName')" for="privilege-profileName">Profile Name</label>
                        <input type="text" class="form-control" name="profileName" id="privilege-profileName"
                            :class="{'valid': !$v.privilege.profileName.$invalid, 'invalid': $v.privilege.profileName.$invalid }" v-model="$v.privilege.profileName.$model"  required/>
                        <div v-if="$v.privilege.profileName.$anyDirty && $v.privilege.profileName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.privilege.profileName.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.privilege.profileName.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.privilege.limit')" for="privilege-limit">Limit</label>
                        <input type="number" class="form-control" name="limit" id="privilege-limit"
                            :class="{'valid': !$v.privilege.limit.$invalid, 'invalid': $v.privilege.limit.$invalid }" v-model.number="$v.privilege.limit.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.privilege.confirmStatus')" for="privilege-confirmStatus">Confirm Status</label>
                        <input type="text" class="form-control" name="confirmStatus" id="privilege-confirmStatus"
                            :class="{'valid': !$v.privilege.confirmStatus.$invalid, 'invalid': $v.privilege.confirmStatus.$invalid }" v-model="$v.privilege.confirmStatus.$model" />
                        <div v-if="$v.privilege.confirmStatus.$anyDirty && $v.privilege.confirmStatus.$invalid">
                            <small class="form-text text-danger" v-if="!$v.privilege.confirmStatus.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 20 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.privilege.effectiveDate')" for="privilege-effectiveDate">Effective Date</label>
                        <div class="d-flex">
                            <input id="privilege-effectiveDate" type="datetime-local" class="form-control" name="effectiveDate" :class="{'valid': !$v.privilege.effectiveDate.$invalid, 'invalid': $v.privilege.effectiveDate.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.privilege.effectiveDate.$model)"
                            @change="updateZonedDateTimeField('effectiveDate', $event)"/>
                        </div>
                        <div v-if="$v.privilege.effectiveDate.$anyDirty && $v.privilege.effectiveDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.privilege.effectiveDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.privilege.effectiveDate.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.privilege.expiryDate')" for="privilege-expiryDate">Expiry Date</label>
                        <div class="d-flex">
                            <input id="privilege-expiryDate" type="datetime-local" class="form-control" name="expiryDate" :class="{'valid': !$v.privilege.expiryDate.$invalid, 'invalid': $v.privilege.expiryDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.privilege.expiryDate.$model)"
                            @change="updateZonedDateTimeField('expiryDate', $event)"/>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.privilege.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./privilege-update.component.ts">
</script>
