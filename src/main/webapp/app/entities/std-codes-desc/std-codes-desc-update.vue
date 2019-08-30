<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.stdCodesDesc.home.createOrEditLabel" v-text="$t('etaxApp.stdCodesDesc.home.createOrEditLabel')">Create or edit a StdCodesDesc</h2>
                <div>
                    <div class="form-group" v-if="stdCodesDesc.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stdCodesDesc.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesDesc.groupCode')" for="std-codes-desc-groupCode">Group Code</label>
                        <input type="text" class="form-control" name="groupCode" id="std-codes-desc-groupCode"
                            :class="{'valid': !$v.stdCodesDesc.groupCode.$invalid, 'invalid': $v.stdCodesDesc.groupCode.$invalid }" v-model="$v.stdCodesDesc.groupCode.$model"  required/>
                        <div v-if="$v.stdCodesDesc.groupCode.$anyDirty && $v.stdCodesDesc.groupCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.groupCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.groupCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesDesc.internalCode')" for="std-codes-desc-internalCode">Internal Code</label>
                        <input type="text" class="form-control" name="internalCode" id="std-codes-desc-internalCode"
                            :class="{'valid': !$v.stdCodesDesc.internalCode.$invalid, 'invalid': $v.stdCodesDesc.internalCode.$invalid }" v-model="$v.stdCodesDesc.internalCode.$model"  required/>
                        <div v-if="$v.stdCodesDesc.internalCode.$anyDirty && $v.stdCodesDesc.internalCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.internalCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.internalCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesDesc.langCode')" for="std-codes-desc-langCode">Lang Code</label>
                        <input type="text" class="form-control" name="langCode" id="std-codes-desc-langCode"
                            :class="{'valid': !$v.stdCodesDesc.langCode.$invalid, 'invalid': $v.stdCodesDesc.langCode.$invalid }" v-model="$v.stdCodesDesc.langCode.$model"  required/>
                        <div v-if="$v.stdCodesDesc.langCode.$anyDirty && $v.stdCodesDesc.langCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.langCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.langCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 10 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesDesc.startDate')" for="std-codes-desc-startDate">Start Date</label>
                        <div class="d-flex">
                            <input id="std-codes-desc-startDate" type="datetime-local" class="form-control" name="startDate" :class="{'valid': !$v.stdCodesDesc.startDate.$invalid, 'invalid': $v.stdCodesDesc.startDate.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.stdCodesDesc.startDate.$model)"
                            @change="updateZonedDateTimeField('startDate', $event)"/>
                        </div>
                        <div v-if="$v.stdCodesDesc.startDate.$anyDirty && $v.stdCodesDesc.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.startDate.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesDesc.endDate')" for="std-codes-desc-endDate">End Date</label>
                        <div class="d-flex">
                            <input id="std-codes-desc-endDate" type="datetime-local" class="form-control" name="endDate" :class="{'valid': !$v.stdCodesDesc.endDate.$invalid, 'invalid': $v.stdCodesDesc.endDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.stdCodesDesc.endDate.$model)"
                            @change="updateZonedDateTimeField('endDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesDesc.externalCode')" for="std-codes-desc-externalCode">External Code</label>
                        <input type="text" class="form-control" name="externalCode" id="std-codes-desc-externalCode"
                            :class="{'valid': !$v.stdCodesDesc.externalCode.$invalid, 'invalid': $v.stdCodesDesc.externalCode.$invalid }" v-model="$v.stdCodesDesc.externalCode.$model" />
                        <div v-if="$v.stdCodesDesc.externalCode.$anyDirty && $v.stdCodesDesc.externalCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.externalCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesDesc.codeDesc')" for="std-codes-desc-codeDesc">Code Desc</label>
                        <input type="text" class="form-control" name="codeDesc" id="std-codes-desc-codeDesc"
                            :class="{'valid': !$v.stdCodesDesc.codeDesc.$invalid, 'invalid': $v.stdCodesDesc.codeDesc.$invalid }" v-model="$v.stdCodesDesc.codeDesc.$model" />
                        <div v-if="$v.stdCodesDesc.codeDesc.$anyDirty && $v.stdCodesDesc.codeDesc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesDesc.codeDesc.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 500 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('etaxApp.stdCodesDesc.stdCodes')" for="std-codes-desc-stdCodes">Std Codes</label>
                        <select class="form-control" id="std-codes-desc-stdCodes" name="stdCodes" v-model="stdCodesDesc.stdCodes">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="stdCodesDesc.stdCodes && stdCodesOption.id === stdCodesDesc.stdCodes.id ? stdCodesDesc.stdCodes : stdCodesOption" v-for="stdCodesOption in stdCodes" :key="stdCodesOption.id">{{stdCodesOption.internalCode}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stdCodesDesc.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./std-codes-desc-update.component.ts">
</script>
