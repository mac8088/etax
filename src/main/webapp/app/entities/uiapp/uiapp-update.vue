<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.uiapp.home.createOrEditLabel" v-text="$t('etaxApp.uiapp.home.createOrEditLabel')">Create or edit a Uiapp</h2>
                <div>
                    <div class="form-group" v-if="uiapp.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="uiapp.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.uiapp.appCode')" for="uiapp-appCode">App Code</label>
                        <input type="text" class="form-control" name="appCode" id="uiapp-appCode"
                            :class="{'valid': !$v.uiapp.appCode.$invalid, 'invalid': $v.uiapp.appCode.$invalid }" v-model="$v.uiapp.appCode.$model"  required/>
                        <div v-if="$v.uiapp.appCode.$anyDirty && $v.uiapp.appCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.uiapp.appCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.uiapp.appCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.uiapp.appName')" for="uiapp-appName">App Name</label>
                        <input type="text" class="form-control" name="appName" id="uiapp-appName"
                            :class="{'valid': !$v.uiapp.appName.$invalid, 'invalid': $v.uiapp.appName.$invalid }" v-model="$v.uiapp.appName.$model" />
                        <div v-if="$v.uiapp.appName.$anyDirty && $v.uiapp.appName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.uiapp.appName.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 200 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.uiapp.appDesc')" for="uiapp-appDesc">App Desc</label>
                        <input type="text" class="form-control" name="appDesc" id="uiapp-appDesc"
                            :class="{'valid': !$v.uiapp.appDesc.$invalid, 'invalid': $v.uiapp.appDesc.$invalid }" v-model="$v.uiapp.appDesc.$model" />
                        <div v-if="$v.uiapp.appDesc.$anyDirty && $v.uiapp.appDesc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.uiapp.appDesc.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 500 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.uiapp.cstdModule')" for="uiapp-cstdModule">Cstd Module</label>
                        <input type="text" class="form-control" name="cstdModule" id="uiapp-cstdModule"
                            :class="{'valid': !$v.uiapp.cstdModule.$invalid, 'invalid': $v.uiapp.cstdModule.$invalid }" v-model="$v.uiapp.cstdModule.$model" />
                        <div v-if="$v.uiapp.cstdModule.$anyDirty && $v.uiapp.cstdModule.$invalid">
                            <small class="form-text text-danger" v-if="!$v.uiapp.cstdModule.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.uiapp.appMessage')" for="uiapp-appMessage">App Message</label>
                        <input type="text" class="form-control" name="appMessage" id="uiapp-appMessage"
                            :class="{'valid': !$v.uiapp.appMessage.$invalid, 'invalid': $v.uiapp.appMessage.$invalid }" v-model="$v.uiapp.appMessage.$model" />
                        <div v-if="$v.uiapp.appMessage.$anyDirty && $v.uiapp.appMessage.$invalid">
                            <small class="form-text text-danger" v-if="!$v.uiapp.appMessage.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 50 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('etaxApp.uiapp.resource')" for="uiapp-resource">Resource</label>
                        <select class="form-control" id="uiapp-resource" multiple name="resource" v-model="uiapp.resources">
                            <option v-bind:value="getSelected(uiapp.resources, resourceOption)" v-for="resourceOption in resources" :key="resourceOption.id">{{resourceOption.resCode}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.uiapp.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./uiapp-update.component.ts">
</script>
