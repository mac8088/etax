<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.stdCodesProp.home.createOrEditLabel" v-text="$t('etaxApp.stdCodesProp.home.createOrEditLabel')">Create or edit a StdCodesProp</h2>
                <div>
                    <div class="form-group" v-if="stdCodesProp.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stdCodesProp.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.groupCode')" for="std-codes-prop-groupCode">Group Code</label>
                        <input type="text" class="form-control" name="groupCode" id="std-codes-prop-groupCode"
                            :class="{'valid': !$v.stdCodesProp.groupCode.$invalid, 'invalid': $v.stdCodesProp.groupCode.$invalid }" v-model="$v.stdCodesProp.groupCode.$model"  required/>
                        <div v-if="$v.stdCodesProp.groupCode.$anyDirty && $v.stdCodesProp.groupCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.groupCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.groupCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.internalCode')" for="std-codes-prop-internalCode">Internal Code</label>
                        <input type="text" class="form-control" name="internalCode" id="std-codes-prop-internalCode"
                            :class="{'valid': !$v.stdCodesProp.internalCode.$invalid, 'invalid': $v.stdCodesProp.internalCode.$invalid }" v-model="$v.stdCodesProp.internalCode.$model"  required/>
                        <div v-if="$v.stdCodesProp.internalCode.$anyDirty && $v.stdCodesProp.internalCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.internalCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.internalCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.propCode')" for="std-codes-prop-propCode">Prop Code</label>
                        <input type="text" class="form-control" name="propCode" id="std-codes-prop-propCode"
                            :class="{'valid': !$v.stdCodesProp.propCode.$invalid, 'invalid': $v.stdCodesProp.propCode.$invalid }" v-model="$v.stdCodesProp.propCode.$model"  required/>
                        <div v-if="$v.stdCodesProp.propCode.$anyDirty && $v.stdCodesProp.propCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.propCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.propCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.startDate')" for="std-codes-prop-startDate">Start Date</label>
                        <div class="d-flex">
                            <input id="std-codes-prop-startDate" type="datetime-local" class="form-control" name="startDate" :class="{'valid': !$v.stdCodesProp.startDate.$invalid, 'invalid': $v.stdCodesProp.startDate.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.stdCodesProp.startDate.$model)"
                            @change="updateZonedDateTimeField('startDate', $event)"/>
                        </div>
                        <div v-if="$v.stdCodesProp.startDate.$anyDirty && $v.stdCodesProp.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.startDate.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.endDate')" for="std-codes-prop-endDate">End Date</label>
                        <div class="d-flex">
                            <input id="std-codes-prop-endDate" type="datetime-local" class="form-control" name="endDate" :class="{'valid': !$v.stdCodesProp.endDate.$invalid, 'invalid': $v.stdCodesProp.endDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.stdCodesProp.endDate.$model)"
                            @change="updateZonedDateTimeField('endDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.valueDate')" for="std-codes-prop-valueDate">Value Date</label>
                        <div class="input-group">
                            <input id="std-codes-prop-valueDate" type="date" class="form-control" name="valueDate"  :class="{'valid': !$v.stdCodesProp.valueDate.$invalid, 'invalid': $v.stdCodesProp.valueDate.$invalid }"
                            v-model="$v.stdCodesProp.valueDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.valueString')" for="std-codes-prop-valueString">Value String</label>
                        <input type="text" class="form-control" name="valueString" id="std-codes-prop-valueString"
                            :class="{'valid': !$v.stdCodesProp.valueString.$invalid, 'invalid': $v.stdCodesProp.valueString.$invalid }" v-model="$v.stdCodesProp.valueString.$model" />
                        <div v-if="$v.stdCodesProp.valueString.$anyDirty && $v.stdCodesProp.valueString.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesProp.valueString.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.valueBool')" for="std-codes-prop-valueBool">Value Bool</label>
                        <input type="checkbox" class="form-check" name="valueBool" id="std-codes-prop-valueBool"
                            :class="{'valid': !$v.stdCodesProp.valueBool.$invalid, 'invalid': $v.stdCodesProp.valueBool.$invalid }" v-model="$v.stdCodesProp.valueBool.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesProp.valueNumber')" for="std-codes-prop-valueNumber">Value Number</label>
                        <input type="number" class="form-control" name="valueNumber" id="std-codes-prop-valueNumber"
                            :class="{'valid': !$v.stdCodesProp.valueNumber.$invalid, 'invalid': $v.stdCodesProp.valueNumber.$invalid }" v-model.number="$v.stdCodesProp.valueNumber.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stdCodesProp.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./std-codes-prop-update.component.ts">
</script>
