<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.officeTaxFunc.home.createOrEditLabel" v-text="$t('etaxApp.officeTaxFunc.home.createOrEditLabel')">Create or edit a OfficeTaxFunc</h2>
                <div>
                    <div class="form-group" v-if="officeTaxFunc.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="officeTaxFunc.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeTaxFunc.taxOfficeId')" for="office-tax-func-taxOfficeId">Tax Office Id</label>
                        <input type="number" class="form-control" name="taxOfficeId" id="office-tax-func-taxOfficeId"
                            :class="{'valid': !$v.officeTaxFunc.taxOfficeId.$invalid, 'invalid': $v.officeTaxFunc.taxOfficeId.$invalid }" v-model.number="$v.officeTaxFunc.taxOfficeId.$model"  required/>
                        <div v-if="$v.officeTaxFunc.taxOfficeId.$anyDirty && $v.officeTaxFunc.taxOfficeId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeTaxFunc.taxOfficeId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeTaxFunc.taxOfficeId.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeTaxFunc.funcOfficeId')" for="office-tax-func-funcOfficeId">Func Office Id</label>
                        <input type="number" class="form-control" name="funcOfficeId" id="office-tax-func-funcOfficeId"
                            :class="{'valid': !$v.officeTaxFunc.funcOfficeId.$invalid, 'invalid': $v.officeTaxFunc.funcOfficeId.$invalid }" v-model.number="$v.officeTaxFunc.funcOfficeId.$model"  required/>
                        <div v-if="$v.officeTaxFunc.funcOfficeId.$anyDirty && $v.officeTaxFunc.funcOfficeId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeTaxFunc.funcOfficeId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeTaxFunc.funcOfficeId.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeTaxFunc.startDate')" for="office-tax-func-startDate">Start Date</label>
                        <div class="input-group">
                            <input id="office-tax-func-startDate" type="date" class="form-control" name="startDate"  :class="{'valid': !$v.officeTaxFunc.startDate.$invalid, 'invalid': $v.officeTaxFunc.startDate.$invalid }"
                            v-model="$v.officeTaxFunc.startDate.$model"  required />
                        </div>
                        <div v-if="$v.officeTaxFunc.startDate.$anyDirty && $v.officeTaxFunc.startDate.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeTaxFunc.startDate.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeTaxFunc.endDate')" for="office-tax-func-endDate">End Date</label>
                        <div class="input-group">
                            <input id="office-tax-func-endDate" type="date" class="form-control" name="endDate"  :class="{'valid': !$v.officeTaxFunc.endDate.$invalid, 'invalid': $v.officeTaxFunc.endDate.$invalid }"
                            v-model="$v.officeTaxFunc.endDate.$model"  />
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.officeTaxFunc.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./office-tax-func-update.component.ts">
</script>
