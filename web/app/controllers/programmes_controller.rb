class ProgrammesController < ApplicationController
  # GET /programmes
  # GET /programmes.json
  def index
    @programmes = Programme.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render :json => @programmes }
    end
  end

  # GET /programmes/1
  # GET /programmes/1.json
  def show
    @programme = Programme.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render :json => @programme }
    end
  end

  # GET /programmes/new
  # GET /programmes/new.json
  def new
    @programme = Programme.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render :json => @programme }
    end
  end

  # GET /programmes/1/edit
  def edit
    @programme = Programme.find(params[:id])
  end

  # POST /programmes
  # POST /programmes.json
  def create
    @programme = Programme.new(params[:programme])

    respond_to do |format|
      if @programme.save
        format.html { redirect_to @programme, :notice => 'Programme was successfully created.' }
        format.json { render :json => @programme, :status => :created, :location => @programme }
      else
        format.html { render :action => "new" }
        format.json { render :json => @programme.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /programmes/1
  # PUT /programmes/1.json
  def update
    @programme = Programme.find(params[:id])

    respond_to do |format|
      if @programme.update_attributes(params[:programme])
        format.html { redirect_to @programme, :notice => 'Programme was successfully updated.' }
        format.json { head :ok }
      else
        format.html { render :action => "edit" }
        format.json { render :json => @programme.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /programmes/1
  # DELETE /programmes/1.json
  def destroy
    @programme = Programme.find(params[:id])
    @programme.destroy

    respond_to do |format|
      format.html { redirect_to programmes_url }
      format.json { head :ok }
    end
  end
end
