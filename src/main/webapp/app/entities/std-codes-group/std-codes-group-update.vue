<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.stdCodesGroup.home.createOrEditLabel" v-text="$t('etaxApp.stdCodesGroup.home.createOrEditLabel')">Create or edit a StdCodesGroup</h2>
                <div>
                    <div class="form-group" v-if="stdCodesGroup.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stdCodesGroup.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.groupCode')" for="std-codes-group-groupCode">Group Code</label>
                        <input type="text" class="form-control" name="groupCode" id="std-codes-group-groupCode"
                            :class="{'valid': !$v.stdCodesGroup.groupCode.$invalid, 'invalid': $v.stdCodesGroup.groupCode.$invalid }" v-model="$v.stdCodesGroup.groupCode.$model"  required/>
                        <div v-if="$v.stdCodesGroup.groupCode.$anyDirty && $v.stdCodesGroup.groupCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.groupCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.groupCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.groupDesc')" for="std-codes-group-groupDesc">Group Desc</label>
                        <input type="text" class="form-control" name="groupDesc" id="std-codes-group-groupDesc"
                            :class="{'valid': !$v.stdCodesGroup.groupDesc.$invalid, 'invalid': $v.stdCodesGroup.groupDesc.$invalid }" v-model="$v.stdCodesGroup.groupDesc.$model"  required/>
                        <div v-if="$v.stdCodesGroup.groupDesc.$anyDirty && $v.stdCodesGroup.groupDesc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.groupDesc.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.groupDesc.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.systemInd')" for="std-codes-group-systemInd">System Ind</label>
                        <select class="form-control" name="systemInd" :class="{'valid': !$v.stdCodesGroup.systemInd.$invalid, 'invalid': $v.stdCodesGroup.systemInd.$invalid }" v-model="$v.stdCodesGroup.systemInd.$model" id="std-codes-group-systemInd"  required>
                            <option value="Y" v-bind:label="$t('etaxApp.OptionIndicator.Y')">Y</option>
                            <option value="N" v-bind:label="$t('etaxApp.OptionIndicator.N')">N</option>
                        </select>
                        <div v-if="$v.stdCodesGroup.systemInd.$anyDirty && $v.stdCodesGroup.systemInd.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.systemInd.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.secLevelRequired')" for="std-codes-group-secLevelRequired">Sec Level Required</label>
                        <select class="form-control" name="secLevelRequired" :class="{'valid': !$v.stdCodesGroup.secLevelRequired.$invalid, 'invalid': $v.stdCodesGroup.secLevelRequired.$invalid }" v-model="$v.stdCodesGroup.secLevelRequired.$model" id="std-codes-group-secLevelRequired"  required>
                            <option value="Y" v-bind:label="$t('etaxApp.OptionIndicator.Y')">Y</option>
                            <option value="N" v-bind:label="$t('etaxApp.OptionIndicator.N')">N</option>
                        </select>
                        <div v-if="$v.stdCodesGroup.secLevelRequired.$anyDirty && $v.stdCodesGroup.secLevelRequired.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.secLevelRequired.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.valueRequired')" for="std-codes-group-valueRequired">Value Required</label>
                        <select class="form-control" name="valueRequired" :class="{'valid': !$v.stdCodesGroup.valueRequired.$invalid, 'invalid': $v.stdCodesGroup.valueRequired.$invalid }" v-model="$v.stdCodesGroup.valueRequired.$model" id="std-codes-group-valueRequired"  required>
                            <option value="Y" v-bind:label="$t('etaxApp.OptionIndicator.Y')">Y</option>
                            <option value="N" v-bind:label="$t('etaxApp.OptionIndicator.N')">N</option>
                        </select>
                        <div v-if="$v.stdCodesGroup.valueRequired.$anyDirty && $v.stdCodesGroup.valueRequired.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.valueRequired.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.valueType')" for="std-codes-group-valueType">Value Type</label>
                        <select class="form-control" name="valueType" :class="{'valid': !$v.stdCodesGroup.valueType.$invalid, 'invalid': $v.stdCodesGroup.valueType.$invalid }" v-model="$v.stdCodesGroup.valueType.$model" id="std-codes-group-valueType" >
                            <option value="D" v-bind:label="$t('etaxApp.ValueTypeIndicator.D')">D</option>
                            <option value="S" v-bind:label="$t('etaxApp.ValueTypeIndicator.S')">S</option>
                            <option value="N" v-bind:label="$t('etaxApp.ValueTypeIndicator.N')">N</option>
                            <option value="B" v-bind:label="$t('etaxApp.ValueTypeIndicator.B')">B</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.descriptionRequired')" for="std-codes-group-descriptionRequired">Description Required</label>
                        <select class="form-control" name="descriptionRequired" :class="{'valid': !$v.stdCodesGroup.descriptionRequired.$invalid, 'invalid': $v.stdCodesGroup.descriptionRequired.$invalid }" v-model="$v.stdCodesGroup.descriptionRequired.$model" id="std-codes-group-descriptionRequired"  required>
                            <option value="Y" v-bind:label="$t('etaxApp.OptionIndicator.Y')">Y</option>
                            <option value="N" v-bind:label="$t('etaxApp.OptionIndicator.N')">N</option>
                        </select>
                        <div v-if="$v.stdCodesGroup.descriptionRequired.$anyDirty && $v.stdCodesGroup.descriptionRequired.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.descriptionRequired.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.externalCodeRequired')" for="std-codes-group-externalCodeRequired">External Code Required</label>
                        <select class="form-control" name="externalCodeRequired" :class="{'valid': !$v.stdCodesGroup.externalCodeRequired.$invalid, 'invalid': $v.stdCodesGroup.externalCodeRequired.$invalid }" v-model="$v.stdCodesGroup.externalCodeRequired.$model" id="std-codes-group-externalCodeRequired" >
                            <option value="Y" v-bind:label="$t('etaxApp.OptionIndicator.Y')">Y</option>
                            <option value="N" v-bind:label="$t('etaxApp.OptionIndicator.N')">N</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.externalCodeLength')" for="std-codes-group-externalCodeLength">External Code Length</label>
                        <input type="number" class="form-control" name="externalCodeLength" id="std-codes-group-externalCodeLength"
                            :class="{'valid': !$v.stdCodesGroup.externalCodeLength.$invalid, 'invalid': $v.stdCodesGroup.externalCodeLength.$invalid }" v-model.number="$v.stdCodesGroup.externalCodeLength.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.parentGroupRequired')" for="std-codes-group-parentGroupRequired">Parent Group Required</label>
                        <select class="form-control" name="parentGroupRequired" :class="{'valid': !$v.stdCodesGroup.parentGroupRequired.$invalid, 'invalid': $v.stdCodesGroup.parentGroupRequired.$invalid }" v-model="$v.stdCodesGroup.parentGroupRequired.$model" id="std-codes-group-parentGroupRequired"  required>
                            <option value="Y" v-bind:label="$t('etaxApp.OptionIndicator.Y')">Y</option>
                            <option value="N" v-bind:label="$t('etaxApp.OptionIndicator.N')">N</option>
                        </select>
                        <div v-if="$v.stdCodesGroup.parentGroupRequired.$anyDirty && $v.stdCodesGroup.parentGroupRequired.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroup.parentGroupRequired.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.parentGroupCode')" for="std-codes-group-parentGroupCode">Parent Group Code</label>
                        <input type="text" class="form-control" name="parentGroupCode" id="std-codes-group-parentGroupCode"
                            :class="{'valid': !$v.stdCodesGroup.parentGroupCode.$invalid, 'invalid': $v.stdCodesGroup.parentGroupCode.$invalid }" v-model="$v.stdCodesGroup.parentGroupCode.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroup.touppercase')" for="std-codes-group-touppercase">Touppercase</label>
                        <input type="checkbox" class="form-check" name="touppercase" id="std-codes-group-touppercase"
                            :class="{'valid': !$v.stdCodesGroup.touppercase.$invalid, 'invalid': $v.stdCodesGroup.touppercase.$invalid }" v-model="$v.stdCodesGroup.touppercase.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stdCodesGroup.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./std-codes-group-update.component.ts">
</script>
