class QuestionsController < ApplicationController
  
  def index
  end
  
  def show
    @question = Question.find(params[:id])
    respond_to do |format|
      format.html
      format.json { render :json => @question }
    end
  end

  def new
    @question = Question.new
  end

  def edit
  end

  def create
  end

  def update
  end

  def destroy
  end

end
