<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.profileSetup.home.createOrEditLabel" v-text="$t('etaxApp.profileSetup.home.createOrEditLabel')">Create or edit a ProfileSetup</h2>
                <div>
                    <div class="form-group" v-if="profileSetup.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="profileSetup.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.profileSetup.profileCode')" for="profile-setup-profileCode">Profile Code</label>
                        <input type="text" class="form-control" name="profileCode" id="profile-setup-profileCode"
                            :class="{'valid': !$v.profileSetup.profileCode.$invalid, 'invalid': $v.profileSetup.profileCode.$invalid }" v-model="$v.profileSetup.profileCode.$model"  required/>
                        <div v-if="$v.profileSetup.profileCode.$anyDirty && $v.profileSetup.profileCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.profileSetup.profileCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.profileSetup.profileCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.profileSetup.cstdOfficeType')" for="profile-setup-cstdOfficeType">Cstd Office Type</label>
                        <input type="text" class="form-control" name="cstdOfficeType" id="profile-setup-cstdOfficeType"
                            :class="{'valid': !$v.profileSetup.cstdOfficeType.$invalid, 'invalid': $v.profileSetup.cstdOfficeType.$invalid }" v-model="$v.profileSetup.cstdOfficeType.$model"  required/>
                        <div v-if="$v.profileSetup.cstdOfficeType.$anyDirty && $v.profileSetup.cstdOfficeType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.profileSetup.cstdOfficeType.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.profileSetup.cstdOfficeType.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.profileSetup.cstdUserType')" for="profile-setup-cstdUserType">Cstd User Type</label>
                        <input type="text" class="form-control" name="cstdUserType" id="profile-setup-cstdUserType"
                            :class="{'valid': !$v.profileSetup.cstdUserType.$invalid, 'invalid': $v.profileSetup.cstdUserType.$invalid }" v-model="$v.profileSetup.cstdUserType.$model"  required/>
                        <div v-if="$v.profileSetup.cstdUserType.$anyDirty && $v.profileSetup.cstdUserType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.profileSetup.cstdUserType.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.profileSetup.cstdUserType.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.profileSetup.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./profile-setup-update.component.ts">
</script>
