<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.resource.home.createOrEditLabel" v-text="$t('etaxApp.resource.home.createOrEditLabel')">Create or edit a Resource</h2>
                <div>
                    <div class="form-group" v-if="resource.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="resource.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.resource.resCode')" for="resource-resCode">Res Code</label>
                        <input type="text" class="form-control" name="resCode" id="resource-resCode"
                            :class="{'valid': !$v.resource.resCode.$invalid, 'invalid': $v.resource.resCode.$invalid }" v-model="$v.resource.resCode.$model"  required/>
                        <div v-if="$v.resource.resCode.$anyDirty && $v.resource.resCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.resource.resCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.resource.resCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 100 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.resource.resName')" for="resource-resName">Res Name</label>
                        <input type="text" class="form-control" name="resName" id="resource-resName"
                            :class="{'valid': !$v.resource.resName.$invalid, 'invalid': $v.resource.resName.$invalid }" v-model="$v.resource.resName.$model" />
                        <div v-if="$v.resource.resName.$anyDirty && $v.resource.resName.$invalid">
                            <small class="form-text text-danger" v-if="!$v.resource.resName.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 200 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.resource.resType')" for="resource-resType">Res Type</label>
                        <select class="form-control" name="resType" :class="{'valid': !$v.resource.resType.$invalid, 'invalid': $v.resource.resType.$invalid }" v-model="$v.resource.resType.$model" id="resource-resType" >
                            <option value="WORKFLOW" v-bind:label="$t('etaxApp.ResourceType.WORKFLOW')">WORKFLOW</option>
                            <option value="CONTROLLER" v-bind:label="$t('etaxApp.ResourceType.CONTROLLER')">CONTROLLER</option>
                            <option value="REST" v-bind:label="$t('etaxApp.ResourceType.REST')">REST</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.resource.appDesc')" for="resource-appDesc">App Desc</label>
                        <input type="text" class="form-control" name="appDesc" id="resource-appDesc"
                            :class="{'valid': !$v.resource.appDesc.$invalid, 'invalid': $v.resource.appDesc.$invalid }" v-model="$v.resource.appDesc.$model" />
                        <div v-if="$v.resource.appDesc.$anyDirty && $v.resource.appDesc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.resource.appDesc.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 500 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.resource.cstdModule')" for="resource-cstdModule">Cstd Module</label>
                        <input type="text" class="form-control" name="cstdModule" id="resource-cstdModule"
                            :class="{'valid': !$v.resource.cstdModule.$invalid, 'invalid': $v.resource.cstdModule.$invalid }" v-model="$v.resource.cstdModule.$model" />
                        <div v-if="$v.resource.cstdModule.$anyDirty && $v.resource.cstdModule.$invalid">
                            <small class="form-text text-danger" v-if="!$v.resource.cstdModule.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.resource.resContent')" for="resource-resContent">Res Content</label>
                        <input type="text" class="form-control" name="resContent" id="resource-resContent"
                            :class="{'valid': !$v.resource.resContent.$invalid, 'invalid': $v.resource.resContent.$invalid }" v-model="$v.resource.resContent.$model" />
                        <div v-if="$v.resource.resContent.$anyDirty && $v.resource.resContent.$invalid">
                            <small class="form-text text-danger" v-if="!$v.resource.resContent.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 500 characters.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.resource.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./resource-update.component.ts">
</script>
