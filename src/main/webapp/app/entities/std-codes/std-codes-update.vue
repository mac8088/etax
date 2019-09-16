<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.stdCodes.home.createOrEditLabel" v-text="$t('etaxApp.stdCodes.home.createOrEditLabel')">Create or edit a StdCodes</h2>
                <div>
                    <div class="form-group" v-if="stdCodes.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stdCodes.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.groupCode')" for="std-codes-groupCode">Group Code</label>
                        <input type="text" class="form-control" name="groupCode" id="std-codes-groupCode"
                            :class="{'valid': !$v.stdCodes.groupCode.$invalid, 'invalid': $v.stdCodes.groupCode.$invalid }" v-model="$v.stdCodes.groupCode.$model"  required/>
                        <div v-if="$v.stdCodes.groupCode.$anyDirty && $v.stdCodes.groupCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodes.groupCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodes.groupCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.internalCode')" for="std-codes-internalCode">Internal Code</label>
                        <input type="text" class="form-control" name="internalCode" id="std-codes-internalCode"
                            :class="{'valid': !$v.stdCodes.internalCode.$invalid, 'invalid': $v.stdCodes.internalCode.$invalid }" v-model="$v.stdCodes.internalCode.$model"  required/>
                        <div v-if="$v.stdCodes.internalCode.$anyDirty && $v.stdCodes.internalCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodes.internalCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodes.internalCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.startDate')" for="std-codes-startDate">Start Date</label>
                        <div class="d-flex">
                            <input id="std-codes-startDate" type="datetime-local" class="form-control" name="startDate" :class="{'valid': !$v.stdCodes.startDate.$invalid, 'invalid': $v.stdCodes.startDate.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.stdCodes.startDate.$model)"
                            @change="updateZonedDateTimeField('startDate', $event)"/>
                        </div>
                        <div v-if="$v.stdCodes.startDate.$anyDirty && $v.stdCodes.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodes.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodes.startDate.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.endDate')" for="std-codes-endDate">End Date</label>
                        <div class="d-flex">
                            <input id="std-codes-endDate" type="datetime-local" class="form-control" name="endDate" :class="{'valid': !$v.stdCodes.endDate.$invalid, 'invalid': $v.stdCodes.endDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.stdCodes.endDate.$model)"
                            @change="updateZonedDateTimeField('endDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.parentInternalCode')" for="std-codes-parentInternalCode">Parent Internal Code</label>
                        <input type="text" class="form-control" name="parentInternalCode" id="std-codes-parentInternalCode"
                            :class="{'valid': !$v.stdCodes.parentInternalCode.$invalid, 'invalid': $v.stdCodes.parentInternalCode.$invalid }" v-model="$v.stdCodes.parentInternalCode.$model" />
                        <div v-if="$v.stdCodes.parentInternalCode.$anyDirty && $v.stdCodes.parentInternalCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodes.parentInternalCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.comments')" for="std-codes-comments">Comments</label>
                        <input type="text" class="form-control" name="comments" id="std-codes-comments"
                            :class="{'valid': !$v.stdCodes.comments.$invalid, 'invalid': $v.stdCodes.comments.$invalid }" v-model="$v.stdCodes.comments.$model" />
                        <div v-if="$v.stdCodes.comments.$anyDirty && $v.stdCodes.comments.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodes.comments.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.secLevel')" for="std-codes-secLevel">Sec Level</label>
                        <input type="number" class="form-control" name="secLevel" id="std-codes-secLevel"
                            :class="{'valid': !$v.stdCodes.secLevel.$invalid, 'invalid': $v.stdCodes.secLevel.$invalid }" v-model.number="$v.stdCodes.secLevel.$model"  required/>
                        <div v-if="$v.stdCodes.secLevel.$anyDirty && $v.stdCodes.secLevel.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodes.secLevel.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodes.secLevel.maxLength" v-bind:value="$t('entity.validation.max')">
                                This field cannot be longer than 99 characters.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodes.secLevel.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.codeValueDate')" for="std-codes-codeValueDate">Code Value Date</label>
                        <div class="input-group">
                            <input id="std-codes-codeValueDate" type="date" class="form-control" name="codeValueDate"  :class="{'valid': !$v.stdCodes.codeValueDate.$invalid, 'invalid': $v.stdCodes.codeValueDate.$invalid }"
                            v-model="$v.stdCodes.codeValueDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.codeValueString')" for="std-codes-codeValueString">Code Value String</label>
                        <input type="text" class="form-control" name="codeValueString" id="std-codes-codeValueString"
                            :class="{'valid': !$v.stdCodes.codeValueString.$invalid, 'invalid': $v.stdCodes.codeValueString.$invalid }" v-model="$v.stdCodes.codeValueString.$model" />
                        <div v-if="$v.stdCodes.codeValueString.$anyDirty && $v.stdCodes.codeValueString.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodes.codeValueString.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.codeValueBool')" for="std-codes-codeValueBool">Code Value Bool</label>
                        <input type="checkbox" class="form-check" name="codeValueBool" id="std-codes-codeValueBool"
                            :class="{'valid': !$v.stdCodes.codeValueBool.$invalid, 'invalid': $v.stdCodes.codeValueBool.$invalid }" v-model="$v.stdCodes.codeValueBool.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodes.codeValueNumber')" for="std-codes-codeValueNumber">Code Value Number</label>
                        <input type="number" class="form-control" name="codeValueNumber" id="std-codes-codeValueNumber"
                            :class="{'valid': !$v.stdCodes.codeValueNumber.$invalid, 'invalid': $v.stdCodes.codeValueNumber.$invalid }" v-model.number="$v.stdCodes.codeValueNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('etaxApp.stdCodes.stdCodesGroup')" for="std-codes-stdCodesGroup">Std Codes Group</label>
                        <select class="form-control" id="std-codes-stdCodesGroup" name="stdCodesGroup" v-model="stdCodes.stdCodesGroup">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="stdCodes.stdCodesGroup && stdCodesGroupOption.id === stdCodes.stdCodesGroup.id ? stdCodes.stdCodesGroup : stdCodesGroupOption" v-for="stdCodesGroupOption in stdCodesGroups" :key="stdCodesGroupOption.id">{{stdCodesGroupOption.groupCode}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stdCodes.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./std-codes-update.component.ts">
</script>
