<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.stdCodesGroupProp.home.createOrEditLabel" v-text="$t('etaxApp.stdCodesGroupProp.home.createOrEditLabel')">Create or edit a StdCodesGroupProp</h2>
                <div>
                    <div class="form-group" v-if="stdCodesGroupProp.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="stdCodesGroupProp.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.groupCode')" for="std-codes-group-prop-groupCode">Group Code</label>
                        <input type="text" class="form-control" name="groupCode" id="std-codes-group-prop-groupCode"
                            :class="{'valid': !$v.stdCodesGroupProp.groupCode.$invalid, 'invalid': $v.stdCodesGroupProp.groupCode.$invalid }" v-model="$v.stdCodesGroupProp.groupCode.$model"  required/>
                        <div v-if="$v.stdCodesGroupProp.groupCode.$anyDirty && $v.stdCodesGroupProp.groupCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.groupCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.groupCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.propCode')" for="std-codes-group-prop-propCode">Prop Code</label>
                        <input type="text" class="form-control" name="propCode" id="std-codes-group-prop-propCode"
                            :class="{'valid': !$v.stdCodesGroupProp.propCode.$invalid, 'invalid': $v.stdCodesGroupProp.propCode.$invalid }" v-model="$v.stdCodesGroupProp.propCode.$model"  required/>
                        <div v-if="$v.stdCodesGroupProp.propCode.$anyDirty && $v.stdCodesGroupProp.propCode.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.propCode.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.propCode.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.propDesc')" for="std-codes-group-prop-propDesc">Prop Desc</label>
                        <input type="text" class="form-control" name="propDesc" id="std-codes-group-prop-propDesc"
                            :class="{'valid': !$v.stdCodesGroupProp.propDesc.$invalid, 'invalid': $v.stdCodesGroupProp.propDesc.$invalid }" v-model="$v.stdCodesGroupProp.propDesc.$model"  required/>
                        <div v-if="$v.stdCodesGroupProp.propDesc.$anyDirty && $v.stdCodesGroupProp.propDesc.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.propDesc.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.propDesc.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.startDate')" for="std-codes-group-prop-startDate">Start Date</label>
                        <div class="d-flex">
                            <input id="std-codes-group-prop-startDate" type="datetime-local" class="form-control" name="startDate" :class="{'valid': !$v.stdCodesGroupProp.startDate.$invalid, 'invalid': $v.stdCodesGroupProp.startDate.$invalid }"
                             required
                            :value="convertDateTimeFromServer($v.stdCodesGroupProp.startDate.$model)"
                            @change="updateZonedDateTimeField('startDate', $event)"/>
                        </div>
                        <div v-if="$v.stdCodesGroupProp.startDate.$anyDirty && $v.stdCodesGroupProp.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.startDate.ZonedDateTimelocal" v-text="$t('entity.validation.ZonedDateTimelocal')">
                                This field should be a date and time.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.endDate')" for="std-codes-group-prop-endDate">End Date</label>
                        <div class="d-flex">
                            <input id="std-codes-group-prop-endDate" type="datetime-local" class="form-control" name="endDate" :class="{'valid': !$v.stdCodesGroupProp.endDate.$invalid, 'invalid': $v.stdCodesGroupProp.endDate.$invalid }"
                            
                            :value="convertDateTimeFromServer($v.stdCodesGroupProp.endDate.$model)"
                            @change="updateZonedDateTimeField('endDate', $event)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.propType')" for="std-codes-group-prop-propType">Prop Type</label>
                        <select class="form-control" name="propType" :class="{'valid': !$v.stdCodesGroupProp.propType.$invalid, 'invalid': $v.stdCodesGroupProp.propType.$invalid }" v-model="$v.stdCodesGroupProp.propType.$model" id="std-codes-group-prop-propType" >
                            <option value="D" v-bind:label="$t('etaxApp.ValueTypeIndicator.D')">D</option>
                            <option value="S" v-bind:label="$t('etaxApp.ValueTypeIndicator.S')">S</option>
                            <option value="N" v-bind:label="$t('etaxApp.ValueTypeIndicator.N')">N</option>
                            <option value="B" v-bind:label="$t('etaxApp.ValueTypeIndicator.B')">B</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.propMdtr')" for="std-codes-group-prop-propMdtr">Prop Mdtr</label>
                        <select class="form-control" name="propMdtr" :class="{'valid': !$v.stdCodesGroupProp.propMdtr.$invalid, 'invalid': $v.stdCodesGroupProp.propMdtr.$invalid }" v-model="$v.stdCodesGroupProp.propMdtr.$model" id="std-codes-group-prop-propMdtr" >
                            <option value="Y" v-bind:label="$t('etaxApp.OptionIndicator.Y')">Y</option>
                            <option value="N" v-bind:label="$t('etaxApp.OptionIndicator.N')">N</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.dfltValueDate')" for="std-codes-group-prop-dfltValueDate">Dflt Value Date</label>
                        <div class="input-group">
                            <input id="std-codes-group-prop-dfltValueDate" type="date" class="form-control" name="dfltValueDate"  :class="{'valid': !$v.stdCodesGroupProp.dfltValueDate.$invalid, 'invalid': $v.stdCodesGroupProp.dfltValueDate.$invalid }"
                            v-model="$v.stdCodesGroupProp.dfltValueDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.dfltValueString')" for="std-codes-group-prop-dfltValueString">Dflt Value String</label>
                        <input type="text" class="form-control" name="dfltValueString" id="std-codes-group-prop-dfltValueString"
                            :class="{'valid': !$v.stdCodesGroupProp.dfltValueString.$invalid, 'invalid': $v.stdCodesGroupProp.dfltValueString.$invalid }" v-model="$v.stdCodesGroupProp.dfltValueString.$model" />
                        <div v-if="$v.stdCodesGroupProp.dfltValueString.$anyDirty && $v.stdCodesGroupProp.dfltValueString.$invalid">
                            <small class="form-text text-danger" v-if="!$v.stdCodesGroupProp.dfltValueString.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 255 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.dfltValueBool')" for="std-codes-group-prop-dfltValueBool">Dflt Value Bool</label>
                        <input type="checkbox" class="form-check" name="dfltValueBool" id="std-codes-group-prop-dfltValueBool"
                            :class="{'valid': !$v.stdCodesGroupProp.dfltValueBool.$invalid, 'invalid': $v.stdCodesGroupProp.dfltValueBool.$invalid }" v-model="$v.stdCodesGroupProp.dfltValueBool.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.stdCodesGroupProp.dfltValueNumber')" for="std-codes-group-prop-dfltValueNumber">Dflt Value Number</label>
                        <input type="number" class="form-control" name="dfltValueNumber" id="std-codes-group-prop-dfltValueNumber"
                            :class="{'valid': !$v.stdCodesGroupProp.dfltValueNumber.$invalid, 'invalid': $v.stdCodesGroupProp.dfltValueNumber.$invalid }" v-model.number="$v.stdCodesGroupProp.dfltValueNumber.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.stdCodesGroupProp.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./std-codes-group-prop-update.component.ts">
</script>
